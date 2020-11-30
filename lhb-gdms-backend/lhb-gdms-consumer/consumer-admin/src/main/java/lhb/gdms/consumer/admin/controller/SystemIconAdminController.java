package lhb.gdms.consumer.admin.controller;

import com.google.common.collect.Maps;
import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.constant.ResponseConstant;
import lhb.gdms.commons.domain.entity.SysUserEntity;
import lhb.gdms.commons.domain.entity.SysUserIconEntity;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.commons.utils.ImageUtils;
import lhb.gdms.configuration.aop.config.PrintlnLog;
import lhb.gdms.consumer.admin.mapper.SysUserIconMapper;
import lhb.gdms.consumer.admin.mapper.SysUserMapper;
import lhb.gdms.consumer.admin.service.SysUserIconService;
import lhb.gdms.consumer.admin.service.SysUserService;
import lhb.gdms.feign.cloud.QiniuFeign;
import lhb.gdms.feign.user.UserFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description  系统设置-头像管理 controller
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/25
 * @time 13:17
 */
@RestController
@CrossOrigin
public class SystemIconAdminController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SysUserIconService sysUserIconService;

    @Resource
    private SysUserIconMapper sysUserIconMapper;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private QiniuFeign qiniuFeign;

    @Resource
    private UserFeign userFeign;

    /**
     * 新增系统头像
     * todo 在没有选择默认头像时，添加一个自动选取id最小的为默认头像
     * @param userIconName
     * @param file 图片，格式为git,png,jpg三种之一,大小不能超过5M
     * @return
     */
    @PostMapping("admin/system/icon")
    public BaseResult insertSystemIcon(String userIconName,
                                       @RequestPart("file") MultipartFile file) throws Exception {

        logger.debug(userIconName);
        logger.debug(file.getOriginalFilename());

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
        if (ImageUtils.imgSizeLimitMax(5 , file).equals("图片正常") == false) {
            return BaseResult.error("图片大小不能超过" + 5 + "M");
        }

        // 校验头像昵称是否重复
        if (sysUserIconService.checkName(userIconName)) {
            return BaseResult.error("该头像昵称已经存在");
        }

        // 生成七牛云key规则:
        String date = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now());
        String key = "lhb-gdms/sys-icon/" + date + "/" + userIconName;

        // 上传图片到七牛云
        BaseResult qiniuResult = qiniuFeign.uploadOne(file, key);
        logger.debug(qiniuResult.get("data").toString());
        logger.debug(qiniuResult.get("code").toString());
        if (qiniuResult.get("code").toString().equals("200") == false) {
            logger.debug("七牛云服务异常");
            return BaseResult.error(HttpConstant.ERROR_MESSAGE);
        }
        Map<String, Object> map =(Map<String, Object>) qiniuResult.get("data");
        String userIconUrl = map.get("url").toString();

        // 封装参数
        SysUserIconEntity sysUserIconEntity = new SysUserIconEntity();
        sysUserIconEntity.setUserIconUrl(userIconUrl);
        sysUserIconEntity.setKey(key);
        sysUserIconEntity.setUserIconName(userIconName);
        sysUserIconEntity.setCreated(new Date());
        sysUserIconEntity.setUpdated(new Date());

        if (sysUserIconService.insert(sysUserIconEntity) > 0) {
            return BaseResult.ok(HttpConstant.UPLOAD_MESSAGE);
        } else {
            // 删除图片
            if (qiniuFeign.deleteOne(key).get("code").toString().equals("200")) {
                return BaseResult.ok("删除成功");
            }
        }

        return BaseResult.error(HttpConstant.UPLOAD_ERROR_MESSAGE);
    }

    /**
     * 删除系统头像
     * @param sysUserIconId
     * @return
     */
    @DeleteMapping("/admin/system/icon/{sysUserIconId}")
    public BaseResult deleteSystemIcon(@PathVariable("sysUserIconId") Long sysUserIconId) throws Exception {
        SysUserIconEntity entity = new SysUserIconEntity();
        entity.setSysUserIconId(sysUserIconId);
        SysUserIconEntity sysUserIconEntity = sysUserIconService.selectOneById(entity);
        logger.debug(sysUserIconEntity.toString());

        // 检查是否有用户使用，若存在则不予删除
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setSysUserKey(sysUserIconEntity.getKey());
        logger.debug(sysUserEntity.getSysUserKey());
        List<SysUserEntity> sysUserEntities = sysUserService.findIconByKey(sysUserEntity);
        if (!sysUserEntities.isEmpty()) {
            return BaseResult.error("此头像有用户在使用中，不允许删除");
        }

        if (sysUserIconEntity.getIsDefault() != null) {
            return BaseResult.error("该头像是系统默认头像，删除前请先解除");
        }

        if (sysUserIconService.deleteOneByKeyWord(sysUserIconEntity) > 0) {
            qiniuFeign.deleteOne(sysUserIconEntity.getKey());
            return BaseResult.ok(HttpConstant.DELETE_MESSAGE);
        }
        return BaseResult.ok(HttpConstant.DELETE_ERROR_MESSAGE);
    }

    /**
     * 设置默认头像
     * @param sysUserIconId
     * @return
     */
    @Transactional
    @PrintlnLog(description = "设置系统默认头像-controller")
    @PutMapping("/admin/system/icon/default/{sysUserIconId}")
    public BaseResult defaultIcon(@PathVariable("sysUserIconId") Long sysUserIconId) {

        if (sysUserIconService.updateDefaultIcon(sysUserIconId) > 0) {
            return BaseResult.ok(HttpConstant.EDIT_MESSAGE);
        }
        return BaseResult.error(HttpConstant.DELETE_ERROR_MESSAGE);
    }

    /**
     * 查看单个列表信息
     * @param sysUserIconId
     * @return
     */
    @PrintlnLog(description = "查看单个列表信息-controller")
    @GetMapping("/admin/system/icon/list/{sysUserIconId}")
    public BaseResult getList(@PathVariable("sysUserIconId") Long sysUserIconId) {
        SysUserIconEntity entity = new SysUserIconEntity();
        entity.setSysUserIconId(sysUserIconId);
        Map<String, Object> map = Maps.newHashMap();
        map.put(ResponseConstant.GET_LIST, sysUserIconService.selectOneById(entity));
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

    /**
     * 查看全部列表信息
     * todo 分页和排序
     * @return
     */
    @PrintlnLog(description = "查看全部列表信息-controller")
    @GetMapping("/admin/system/icon/lists")
    public BaseResult getLists() {
        Map<String, Object> map = Maps.newHashMap();
        List<SysUserIconEntity> list = sysUserIconService.select();
        map.put(ResponseConstant.GET_LIST, list);
        map.put(ResponseConstant.COUNT, list.size());
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

}
