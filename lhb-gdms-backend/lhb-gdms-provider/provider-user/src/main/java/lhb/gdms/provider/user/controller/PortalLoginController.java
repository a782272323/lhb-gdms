package lhb.gdms.provider.user.controller;

import com.google.common.collect.Maps;
import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.constant.ResponseConstant;
import lhb.gdms.commons.domain.entity.SysUserEntity;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.commons.utils.MapperUtils;
import lhb.gdms.commons.utils.RedisUtils;
import lhb.gdms.commons.utils.RegularExpressionUtil;
import lhb.gdms.configuration.aop.config.PrintlnLog;
import lhb.gdms.configuration.utils.SecurityOauth2Utils;
import lhb.gdms.provider.user.domain.vo.LoginInfoVO;
import lhb.gdms.provider.user.domain.vo.LoginPortalParamVO;
import lhb.gdms.provider.user.domain.vo.RegisteredParamsVO;
import lhb.gdms.provider.user.mapper.SysUserMapper;
import lhb.gdms.provider.user.service.PortalLoginService;
import lhb.gdms.provider.user.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Description  门户网站登录处理
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/22
 * @time 10:32
 */
@RestController
@CrossOrigin
public class PortalLoginController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PortalLoginService portalLoginService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTokenStore tokenStore;

    @Autowired
    private SecurityOauth2Utils securityOauth2Utils;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 门户网站用户登录
     * @param loginPortalParamVO
     * @return
     */
    @PrintlnLog(description = "门户网站用户登录详情-controller")
    @PostMapping("/user/login/portal")
    public BaseResult portalLogin(@RequestBody LoginPortalParamVO loginPortalParamVO) {
        logger.debug(loginPortalParamVO.toString());
        // 规则校验
        switch (loginPortalParamVO.getLoginType()) {
            // 密码登录
            case 1:
                if (StringUtils.isBlank(loginPortalParamVO.getUsername())) {
                    return BaseResult.error(RegularExpressionUtil.REGEX_USERNAME_NULL);
                } else if(StringUtils.isBlank(loginPortalParamVO.getPassword())) {
                    return BaseResult.error(RegularExpressionUtil.REGEX_PASSWORD_NULL);
                }
                break;
            // 手机登录
            case 2:
                if (StringUtils.isBlank(loginPortalParamVO.getPhone())) {
                    return BaseResult.error(RegularExpressionUtil.REGEX_MOBILE_NULL);
                } else if (!RegularExpressionUtil.isMobile(loginPortalParamVO.getPhone())) {
                    return BaseResult.error(RegularExpressionUtil.REGEX_MOBILE_ERROR);
                } else if (StringUtils.isBlank(loginPortalParamVO.getPhoneCode())) {
                    return BaseResult.error(HttpConstant.CODE_NULL_MESSAGE);
                } else if (sysUserMapper.checkSysUserPhone(loginPortalParamVO.getPhone(), "+86") == null) {
                    return BaseResult.error("该手机号并未注册，不能用于登录系统!");
                }
                break;
            // 邮箱登录
            case 3:
                if (StringUtils.isBlank(loginPortalParamVO.getEmail())) {
                    return BaseResult.error(RegularExpressionUtil.REGEX_EMAIL_NULL);
                } else if (!RegularExpressionUtil.isEmail(loginPortalParamVO.getPhone())) {
                    return BaseResult.error(RegularExpressionUtil.REGEX_EMAIL_ERROR);
                } else if (StringUtils.isBlank(loginPortalParamVO.getEmailCode())) {
                    return BaseResult.error(HttpConstant.CODE_NULL_MESSAGE);
                } else if (sysUserMapper.checkSysUserEmail(loginPortalParamVO.getEmail()) == null) {
                    return BaseResult.error("该邮箱并未注册，不能用于登录系统!");
                }
                break;
            default: break;
        }

        return portalLoginService.portalLogin(loginPortalParamVO);
    }

    /**
     * 获取用户信息，用于登录成功后跳转首页
     * @param authentication
     * @return
     */
    @PrintlnLog(description = "获取用户信息，用于登录成功后跳转首页--controller")
    @GetMapping("/user/info/portal")
    public BaseResult portalInfo(Authentication authentication) throws Exception{
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setSysUserId(sysUserId);
        SysUserEntity entity = sysUserMapper.getListById(sysUserId);
        LoginInfoVO loginInfoVO = new LoginInfoVO();
        loginInfoVO.setName(entity.getSysUserUsername());
        loginInfoVO.setAvatar(entity.getSysUserIcon());
        loginInfoVO.setRoles("Average User");
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, loginInfoVO);
    }

    /**
     * 门户网站注销
     * @param request
     * @return
     */
    @PostMapping("/user/logout/portal")
    public BaseResult portalLogout(HttpServletRequest request) {
        String access_token = request.getParameter("access_token");
        logger.debug(access_token);
        if (StringUtils.isNotBlank(access_token)) {
            logger.debug("门户网站用户,删除token");
            logger.debug(tokenStore.readAccessToken(access_token).getValue());
            tokenStore.removeAccessToken(tokenStore.readAccessToken(access_token));
        }
        return BaseResult.ok(HttpConstant.LOGOUT_OK_MESSAGE);
    }

    /**
     * 门户网站用户注册
     * @param registeredParamsVO
     * @return
     */
    @PrintlnLog(description = "门户网站用户注册详情-controller")
    @PostMapping("/user/registered/portal/{code}")
    public BaseResult portalRegistered(@PathVariable("code") String code,
                                       @RequestBody RegisteredParamsVO registeredParamsVO) {
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setSysUserUsername(registeredParamsVO.getUsername());
        // 密码加密
        sysUserEntity.setSysUserPassword(passwordEncoder.encode(registeredParamsVO.getPassword()));
        sysUserEntity.setSysUserPhone(registeredParamsVO.getPhone());
        sysUserEntity.setSysUserEmail(registeredParamsVO.getEmail());

        // 有效性校验
        // 用户名不能为空
        if (StringUtils.isBlank(sysUserEntity.getSysUserUsername())) {
            return BaseResult.error(RegularExpressionUtil.REGEX_USERNAME_NULL);
        }
        // 用户名格式错误
        if (!RegularExpressionUtil.isUsername(sysUserEntity.getSysUserUsername())) {
            return BaseResult.error(RegularExpressionUtil.REGEX_USERNAME_ERROR);
        }
        // 密码不能为空
        if (StringUtils.isBlank(registeredParamsVO.getPassword())) {
            return BaseResult.error(RegularExpressionUtil.REGEX_PASSWORD_NULL);
        }
        // 密码格式错误
        if (!RegularExpressionUtil.isPassword(registeredParamsVO.getPassword())) {
            return BaseResult.error(RegularExpressionUtil.REGEX_PASSWORD_ERROR);
        }
        // 手机不能为空
        if (StringUtils.isBlank(sysUserEntity.getSysUserPhone())) {
            return BaseResult.error(RegularExpressionUtil.REGEX_MOBILE_NULL);
        }
        // 手机格式错误
        if (!RegularExpressionUtil.isMobile(sysUserEntity.getSysUserPhone())) {
            return BaseResult.error(RegularExpressionUtil.REGEX_MOBILE_ERROR);
        }
        // 重复性校验
        if (sysUserMapper.checkSysUserPhone(sysUserEntity.getSysUserPhone(), sysUserEntity.getSysUserAreaCode()) != null) {
            return BaseResult.error("该手机号码(" + sysUserEntity.getSysUserPhone() + ")已经被注册!");
        }
        if (sysUserMapper.checkSysUsername(sysUserEntity.getSysUserUsername()) != null) {
            return BaseResult.error("用户名(" + sysUserEntity.getSysUserUsername() + ")已经存在!");
        }
        // 校验手机验证码
        String key = "lhb:gdms:aliyun:registered:sms:code:" + registeredParamsVO.getPhone();
        String checkCode = (String) redisUtils.getValueByKey(key);
        if (code.length() != 6) {
            return BaseResult.error("请输入6位纯数字验证码");
        }
        if (StringUtils.isBlank(checkCode)) {
            return BaseResult.error(HttpConstant.INVALID_CODE_MESSAGE);
        }
        if (code.trim().equals(checkCode.trim()) == false) {
            return BaseResult.error(HttpConstant.ERROR_CODE_MESSAGE);
        } else {
            redisUtils.deleteCache(key);
        }


        return portalLoginService.portalRegistered(sysUserEntity);
    }


}
