package lhb.gdms.provider.user.service.impl;

import com.google.common.collect.Maps;
import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.constant.ResponseConstant;
import lhb.gdms.commons.domain.entity.SysUserEntity;
import lhb.gdms.commons.domain.entity.SysUserIconEntity;
import lhb.gdms.commons.domain.entity.SysUserPhoneEmailEntity;
import lhb.gdms.commons.domain.entity.SysUserRoleEntity;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.commons.utils.RedisUtils;
import lhb.gdms.commons.utils.UUIDUtils;
import lhb.gdms.configuration.aop.config.PrintlnLog;
import lhb.gdms.feign.cloud.Oauth2Feign;
import lhb.gdms.provider.user.domain.vo.LoginPortalParamVO;
import lhb.gdms.provider.user.mapper.SysUserIconMapper;
import lhb.gdms.provider.user.mapper.SysUserMapper;
import lhb.gdms.provider.user.service.PortalLoginService;
import lhb.gdms.provider.user.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class PortalLoginServiceImpl implements PortalLoginService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisTokenStore tokenStore;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserIconMapper sysUserIconMapper;

    @Autowired
    private Oauth2Feign oauth2Feign;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 注册
     * @param sysUserEntity
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public BaseResult portalRegistered(SysUserEntity sysUserEntity) {

        // 生成默认的用户昵称
        String uuid = "";
        for (int i = 0; i < 5; i++) {
            uuid = UUID.randomUUID().toString();
            uuid = uuid.replace("-", "").substring(0,12);
        }
        String sysUserNickname = "Bin-" + uuid;
        sysUserEntity.setSysUserAreaCode("+86");
        sysUserEntity.setSysUserNickname(sysUserNickname);
        sysUserEntity.setSysUserStatus(1);
        sysUserEntity.setSysUserType(1);
        sysUserEntity.setCreated(new Date());
        sysUserEntity.setUpdated(new Date());
        sysUserEntity.setSysUserIntroduction("该用户很懒，没有写简介");
        // 获取系统默认头像
        SysUserIconEntity sysUserIconEntity = sysUserIconMapper.findDefaultUrlInfo();
        sysUserEntity.setSysUserIcon(sysUserIconEntity.getUserIconUrl());
        sysUserEntity.setSysUserSex("男");

        // 把信息添加进入账户表
        sysUserMapper.insert(sysUserEntity);
        logger.debug(sysUserEntity.toString());
        // 赋予平台用户角色
        SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
        sysUserRoleEntity.setSysUserId(sysUserEntity.getSysUserId());
        sysUserRoleEntity.setRoleId((long) 1);
        sysUserRoleEntity.setCreated(new Date());
        sysUserRoleEntity.setUpdated(new Date());
        sysUserMapper.insertSysUserRole(sysUserRoleEntity);

        // 添加角色邮箱手机绑定关系表
        SysUserPhoneEmailEntity sysUserPhoneEmailEntity = new SysUserPhoneEmailEntity();
        sysUserPhoneEmailEntity.setIsPhone(1);
        sysUserPhoneEmailEntity.setIsEmail(0);
        sysUserPhoneEmailEntity.setSysUserId(sysUserEntity.getSysUserId());
        sysUserMapper.insertSysUserPhoneEmail(sysUserPhoneEmailEntity);

        return BaseResult.ok(HttpConstant.REGISTERED_OK_MESSAGE);
    }

    /**
     * 门户网站用户登录
     * @param loginPortalParamVO
     * @return
     */
    @Override
    public BaseResult portalLogin(LoginPortalParamVO loginPortalParamVO) {

        String clientId = "portal";
        String client_secret = "123456";
        String scope = "web-portal";

        // 判断登录类型
        switch (loginPortalParamVO.getLoginType()) {
            // 密码登录
            case 1:
                // 校验用户密码
                SysUserEntity sysUserEntity = new SysUserEntity();
                sysUserEntity.setSysUserUsername(loginPortalParamVO.getUsername());
                SysUserEntity entity = sysUserService.selectOneByKeyWord(sysUserEntity);
                if (entity == null || !passwordEncoder.matches(loginPortalParamVO.getPassword(), entity.getSysUserPassword())) {
                    return BaseResult.error(HttpConstant.LOGIN_ERROR_MESSAGE);
                }
                logger.debug("门户网站用户账户密码登录");
                // 拼接参数，请求token
                Map<String, Object> params = Maps.newHashMap();
                params.put("grant_type", "password");
                params.put("username", loginPortalParamVO.getUsername());
                params.put("password", loginPortalParamVO.getPassword());
                params.put("scope", scope);
                params.put("client_id", clientId);
                params.put("client_secret", client_secret);
                // 请求token
                BaseResult result = oauth2Feign.getToken(params);
                logger.debug("-----result-----： {}",result.toString());
                String access_token = result.get("access_token").toString();
                if (StringUtils.isNotBlank(access_token)) {
                    return BaseResult.ok().put(HttpConstant.OK, HttpConstant.LOGIN_OK_MESSAGE, ResponseConstant.DATA, loginToken(access_token, result));
                }
            // 手机登录
            case 2:
                // 校验验证码
                String phoneKey = "lhb:gdms:aliyun:sms:code:" + loginPortalParamVO.getPhone();
                String checkPhoneCode = (String) redisUtils.getValueByKey(phoneKey);
                if (StringUtils.isBlank(checkPhoneCode)) {
                    return BaseResult.error(HttpConstant.INVALID_CODE_MESSAGE);
                }
                if (checkPhoneCode.equals(loginPortalParamVO.getPhoneCode())) {
                    logger.debug("门户网站用户短信登录，校验成功");
                    // 拼接参数，请求token
                    Map<String, Object> paramsPhone = Maps.newHashMap();
                    paramsPhone.put("grant_type", "sms");
                    paramsPhone.put("sms", loginPortalParamVO.getPhone());
                    paramsPhone.put("scope", scope);
                    paramsPhone.put("client_id", clientId);
                    paramsPhone.put("client_secret", client_secret);
                    // 请求token
                    BaseResult resultPhone = oauth2Feign.getToken(paramsPhone);
                    logger.debug("-----result-----： {}",resultPhone.toString());
                    String access_token_phone = resultPhone.get("access_token").toString();
                    if (StringUtils.isNotBlank(access_token_phone)) {
                        redisUtils.deleteCache(phoneKey);
                        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.LOGIN_OK_MESSAGE, ResponseConstant.DATA, loginToken(access_token_phone, resultPhone));
                    }
                }
                return BaseResult.error(HttpConstant.ERROR_CODE_MESSAGE);

            // 邮箱登录
            case 3:
                // 校验验证码
                String emailKey = "lhb:gdms:qq:mail:code:" + loginPortalParamVO.getEmail();
                String checkEmailCode = (String) redisUtils.getValueByKey(emailKey);
                if (StringUtils.isBlank(checkEmailCode)) {
                    return BaseResult.error(HttpConstant.INVALID_CODE_MESSAGE);
                }
                if (checkEmailCode.equals(loginPortalParamVO.getEmailCode())) {
                    logger.debug("门户网站用户邮箱登录，校验成功");
                    // 拼接参数，请求token
                    Map<String, Object> paramsEmail = Maps.newHashMap();
                    paramsEmail.put("grant_type", "email");
                    paramsEmail.put("email", loginPortalParamVO.getEmail());
                    paramsEmail.put("scope", scope);
                    paramsEmail.put("client_id", clientId);
                    paramsEmail.put("client_secret", client_secret);
                    // 请求token
                    BaseResult resultEmail = oauth2Feign.getToken(paramsEmail);
                    logger.debug("-----result-----： {}",resultEmail.toString());
                    String access_token_email = resultEmail.get("access_token").toString();
                    if (StringUtils.isNotBlank(access_token_email)) {
                        redisUtils.deleteCache(emailKey);
                        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.LOGIN_OK_MESSAGE, ResponseConstant.DATA, loginToken(access_token_email, resultEmail));
                    }
                }
                return BaseResult.error(HttpConstant.ERROR_CODE_MESSAGE);
            default:
                return BaseResult.error(HttpConstant.ERROR_MESSAGE);
        }
    }

    /**
     * 封装登录信息返回给前端
     * @param access_token
     * @param result
     * @return
     */
    private Map<String, Object> loginToken(String access_token, BaseResult result) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("access_token", access_token);
        map.put("token_type", result.get("token_type"));
        map.put("refresh_token", result.get("refresh_token"));
        map.put("expires_in", result.get("expires_in"));
        map.put("scope", result.get("scope"));
        map.put("token", access_token);
        return map;
    }
}
