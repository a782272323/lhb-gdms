package lhb.gdms.consumer.blog.controller;

import com.google.common.collect.Maps;
import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.constant.ResponseConstant;
import lhb.gdms.commons.domain.entity.SysUserEntity;
import lhb.gdms.commons.domain.entity.SysUserIconEntity;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.commons.utils.ImageUtils;
import lhb.gdms.commons.utils.RegularExpressionUtil;
import lhb.gdms.commons.utils.TimeUtils;
import lhb.gdms.configuration.aop.config.PrintlnLog;
import lhb.gdms.configuration.utils.SecurityOauth2Utils;
import lhb.gdms.consumer.blog.mapper.OtherMapper;
import lhb.gdms.consumer.blog.mapper.SysUserMapper;
import lhb.gdms.consumer.blog.service.SysUserService;
import lhb.gdms.feign.cloud.AliyunSmsFeign;
import lhb.gdms.feign.cloud.QQMailFeign;
import lhb.gdms.feign.cloud.QiniuFeign;
import lhb.gdms.feign.user.UserFeign;
import lhb.gdms.feign.utils.UserFeignUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

/**
 * @author Herbie Leung(梁鸿斌)
 * @Description 个人中心-设置 controller
 * @date 2020/12/18
 * @time 14:03
 */
@RestController
@CrossOrigin
public class SystemController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OtherMapper otherMapper;

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private QiniuFeign qiniuFeign;

    @Autowired
    private UserFeignUtils userFeignUtils;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SecurityOauth2Utils securityOauth2Utils;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private AliyunSmsFeign aliyunSmsFeign;

    @Autowired
    private QQMailFeign qqMailFeign;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 查询系统头像信息
     *
     * @return
     */
    @PrintlnLog(description = "查询系统头像信息-controller")
    @GetMapping("/blog/person/center/system/iconLists")
    public BaseResult getIconLists() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("getLists", otherMapper.getIconLists());
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

    /**
     * 用户更改系统头像
     * @param sysUserId
     * @param sysIconId
     * @return
     */
    @PrintlnLog(description = "用户更改系统头像-controller")
    @PutMapping("/blog/person/center/system/icon/{sysUserId}/{sysIconId}")
    public BaseResult updateUserSystemIcon(@PathVariable("sysUserId") Long sysUserId,
                                           @PathVariable("sysIconId") Long sysIconId) throws Exception{

        SysUserIconEntity sysUserIconEntity = otherMapper.getIconInfoById(sysIconId);
        // 查询用户信息
        SysUserEntity sysUserEntity = sysUserMapper.getUserInfoByUserId(sysUserId);
        if (StringUtils.isNotBlank(sysUserEntity.getSysUserKey())) {
            // 删除七牛云上的key
            logger.debug(",删除七牛云的key = " + sysUserEntity.getSysUserKey());
            BaseResult deleteResult = qiniuFeign.deleteOne(sysUserEntity.getSysUserKey());
        }
        sysUserEntity.setSysUserId(sysUserId);
        sysUserEntity.setSysUserIcon(sysUserIconEntity.getUserIconUrl());
        sysUserEntity.setSysUserKey(null);
        sysUserEntity.setUpdated(new Date());
        return userFeign.updateUserSysIcon(sysUserEntity);
    }

    /**
     * 用户自定义头像
     * @param sysUserId
     * @param file
     * @return
     */
    @PostMapping("/blog/person/center/system/icon/{sysUserId}")
    public BaseResult updateUserIcon(@PathVariable("sysUserId") Long sysUserId,
                                     @RequestPart("file") MultipartFile file) throws Exception{
        // 校验图片格式
        if (ImageUtils.isImgFormat(file, "jpg").equals("文件名不正确") &&
                ImageUtils.isImgFormat(file, "png").equals("文件名不正确")) {
            return BaseResult.error(HttpConstant.ERROR, "文件名不正确");
        }
        if (ImageUtils.isImgFormat(file, "jpg").equals("图片不正常") &&
                ImageUtils.isImgFormat(file, "png").equals("图片不正常")) {
            return BaseResult.error(HttpConstant.ERROR, "文件只支持jpg或png格式");
        }
        // 校验图片大小
        if (ImageUtils.imgSizeLimitMax(5, file).equals("图片正常") == false) {
            return BaseResult.error("图片大小不能超过" + 5 + "M");
        }

        // 查询用户数据
        SysUserEntity sysUserEntity = userFeignUtils.getSysUserInfo(userFeign.getList(sysUserId));

        String date = DateTimeFormatter.ofPattern(TimeUtils.YYYY_MM_DD_AND_HH_MM_SS).format(LocalDateTime.now());
        String key = "lhb-gdms/sys-user/" + sysUserId + "/" + date;
        logger.debug(file.getOriginalFilename() + "新的 key = " + key);
        if (deleteOldUserIconKey(sysUserEntity.getSysUserKey(), qiniuFeign, "用户自定义头像") == false) {
            return BaseResult.error(HttpConstant.ERROR_MESSAGE);
        }
        // 上传图片到七牛云
        BaseResult qiniuResult = qiniuFeign.uploadOne(file, key);
        if (qiniuResult.get("code").toString().equals("200") == false) {
            return BaseResult.error(HttpConstant.ERROR_MESSAGE);
        }
        Map<String, Object> qiniuMap = (Map<String, Object>) qiniuResult.get("data");
        String url = qiniuMap.get("url").toString();
        logger.debug("url = " + url);
        // 封装参数
        sysUserEntity.setSysUserId(sysUserId);
        sysUserEntity.setSysUserIcon(url);
        sysUserEntity.setSysUserKey(key);
        sysUserEntity.setUpdated(new Date());

        return userFeign.updateUserSysIcon(sysUserEntity);
    }

    /**
     * 修改用户基础信息
     * @param entity
     * @return
     */
    @PrintlnLog(description = "修改用户基础信息-controller")
    @PutMapping("/blog/person/center/system/base/{sysUserId}")
    public BaseResult updateSysUserBase(@PathVariable("sysUserId") Long sysUserId,
                                       @RequestBody SysUserEntity entity) {
        entity.setSysUserId(sysUserId);
        if (StringUtils.isNotBlank(entity.getSysUserPhone()) && !RegularExpressionUtil.isMobile(entity.getSysUserPhone())) {
            return BaseResult.error(RegularExpressionUtil.REGEX_MOBILE_ERROR);
        }
        if (StringUtils.isNotBlank(entity.getSysUserEmail()) && !RegularExpressionUtil.isEmail(entity.getSysUserEmail())) {
            return BaseResult.error(RegularExpressionUtil.REGEX_EMAIL_ERROR);
        }
        if (sysUserService.updateUserBaseInfo(entity) < 0) {
            return BaseResult.error(HttpConstant.EDIT_ERROR_MESSAGE);
        }
        return BaseResult.ok(HttpConstant.EDIT_MESSAGE);
    }

    /**
     * 获取用户信息详情
     * @param authentication
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "获取用户信息详情-controller")
    @GetMapping("/blog/person/center/system/details")
    public BaseResult getUserDetails(Authentication authentication) throws Exception{
        Map<String, Object> map = Maps.newHashMap();
        SysUserEntity sysUserEntity = securityOauth2Utils.getEntity(authentication);
        SysUserEntity entity = sysUserService.selectOneById(sysUserEntity);
        map.put(ResponseConstant.GET_LIST, entity);
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

    /**
     * 发送短信验证码
     * @param phone
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "发送短信验证码-controller")
    @PostMapping("/blog/person/center/system/send/phone-code")
    public BaseResult sendPhoneCode(@RequestParam("phone") String phone) throws Exception {
        return aliyunSmsFeign.checkCode(phone);
    }

    /**
     * 校验手机验证码
     * @param phone
     * @param code
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "校验手机验证码-controller")
    @GetMapping("/blog/person/center/system/check/phone-code")
    public BaseResult checkPhoneCode(@RequestParam("phone") String phone,
                                     @RequestParam("code") String code) throws Exception {
        return aliyunSmsFeign.getCode(phone, code);
    }

    /**
     * 发送邮箱验证码
     * @param to
     * @return
     */
    @PrintlnLog(description = "发送邮箱验证码-controller")
    @PostMapping("/blog/person/center/system/send/email-code")
    public BaseResult sendEmailCode(@RequestParam("to") String to) {
        return qqMailFeign.sendCodeMailByQQ(to);
    }

    /**
     * 校验邮箱验证码
     * @param to
     * @param code
     * @return
     */
    @PrintlnLog(description = "校验邮箱验证码-controller")
    @GetMapping("/blog/person/center/system/check/email-code")
    public BaseResult checkEmailCode(@RequestParam("to") String to,
                                     @RequestParam("code") String code) {
        return qqMailFeign.checkCodeMailByQQ(to, code);
    }

    /**
     * 重置密码
     * @param authentication
     * @param password
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "重置密码-controller")
    @PostMapping("/blog/person/center/system/reset/password")
    public BaseResult resetPassword(Authentication authentication, String password) throws Exception {
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        SysUserEntity entity = new SysUserEntity();
        entity.setSysUserId(sysUserId);
        entity.setSysUserPassword(passwordEncoder.encode(password));
        entity.setUpdated(new Date());
        return sysUserService.resetPassword(entity) > 0
                ? BaseResult.ok("密码重置成功!")
                : BaseResult.error(HttpConstant.ERROR_MESSAGE);
    }

    /**
     * 忘记密码-重置密码
     * @param keyword 邮箱或者手机号码
     * @param password 密码
     * @param code 验证码
     * @param type 类型邮箱或者手机 phone email
     * @return
     */
    @PrintlnLog(description = "忘记密码-重置密码-controller")
    @PostMapping("/web/blog/person/center/system/reset/password")
    public BaseResult resetPasswordWithoutLogin(String keyword, String password,
                                                String code, String type) {
        String phone = "phone";
        String email = "email";
        SysUserEntity entity = new SysUserEntity();

        if (type.equals(phone)) {
            logger.debug("手机");
            // 校验验证码
            BaseResult result = aliyunSmsFeign.getCode(keyword, code);
            if (result.get("message").equals(HttpConstant.CODE_OK_MESSAGE) == false) {
                return BaseResult.error(result.get("message").toString());
            }
            // 查询用户id
            entity = sysUserService.getUserInfoByPhone(keyword);
        }
        if (type.equals(email)) {
            logger.debug("邮箱");
            // 校验验证码
            BaseResult result = qqMailFeign.checkCodeMailByQQ(keyword, code);
            if (result.get("message").equals(HttpConstant.CODE_OK_MESSAGE) == false) {
                return BaseResult.error(result.get("message").toString());
            }
            // 查询用户id
            entity = sysUserService.getUserInfoByEmail(keyword);
        }
        // 重置密码
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setSysUserId(entity.getSysUserId());
        sysUserEntity.setUpdated(new Date());
        sysUserEntity.setSysUserPassword(passwordEncoder.encode(password));
        return sysUserService.resetPassword(sysUserEntity) > 0
                ? BaseResult.ok("密码重置成功")
                : BaseResult.error(HttpConstant.ERROR_MESSAGE);
    }

    /**
     * 判断该用户修改头像时，之前是否上传了旧头像
     * @param key
     * @return
     */
    public boolean deleteOldUserIconKey(String key, QiniuFeign qiniuFeign, String logString) throws Exception{
        if (StringUtils.isNotBlank(key)) {
            // 删除七牛云上的key
            logger.debug(logString + ",删除七牛云的key = " + key);
            BaseResult deleteResult = qiniuFeign.deleteOne(key);
//            if (deleteResult.get("code").toString().equals("200") == false) {
//
//            }
            return false;
        }
        return true;
    }


}
