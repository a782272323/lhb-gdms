package lhb.gdms.provider.user.controller;

import lhb.gdms.commons.domain.entity.SysUserEntity;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.commons.utils.RegularExpressionUtil;
import lhb.gdms.configuration.aop.config.PrintlnLog;
import lhb.gdms.provider.user.service.PortalLoginService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 门户网站用户登录
     * @param sysUserEntity
     * @return
     */
    @PrintlnLog(description = "门户网站用户登录详情-controller")
    @PostMapping("/user/login/portal")
    public BaseResult portalLogin(@RequestBody SysUserEntity sysUserEntity) {
        logger.debug(sysUserEntity.toString());
        return null;
    }

    @PrintlnLog(description = "门户网站用户注册详情-controller")
    @PostMapping("/user/registered/portal")
    public BaseResult portalRegistered(@RequestBody SysUserEntity sysUserEntity) {

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
        if (StringUtils.isBlank(sysUserEntity.getSysUserPassword())) {
            return BaseResult.error(RegularExpressionUtil.REGEX_PASSWORD_NULL);
        }
        // 密码格式错误
        if (!RegularExpressionUtil.isPassword(sysUserEntity.getSysUserPassword())) {
            return BaseResult.error(RegularExpressionUtil.REGEX_PASSWORD_ERROR);
        }
        sysUserEntity.setSysUserPassword(passwordEncoder.encode(sysUserEntity.getSysUserPassword()));
        // 手机不能为空
        if (StringUtils.isBlank(sysUserEntity.getSysUserPhone())) {
            return BaseResult.error(RegularExpressionUtil.REGEX_MOBILE_NULL);
        }
        // 手机格式错误
        if (!RegularExpressionUtil.isMobile(sysUserEntity.getSysUserPhone())) {
            return BaseResult.error(RegularExpressionUtil.REGEX_MOBILE_ERROR);
        }
        // 邮箱不能为空
        if (StringUtils.isBlank(sysUserEntity.getSysUserEmail())) {
            return BaseResult.error(RegularExpressionUtil.REGEX_EMAIL_NULL);
        }
        // 邮箱格式错误
        if (!RegularExpressionUtil.isEmail(sysUserEntity.getSysUserEmail())) {
            return BaseResult.error(RegularExpressionUtil.REGEX_EMAIL_ERROR);
        }
        return portalLoginService.portalRegistered(sysUserEntity);
    }



}
