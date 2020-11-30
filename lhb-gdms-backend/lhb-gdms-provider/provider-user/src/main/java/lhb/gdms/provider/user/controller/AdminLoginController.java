package lhb.gdms.provider.user.controller;

import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.constant.ResponseConstant;
import lhb.gdms.commons.domain.entity.SysUserEntity;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.configuration.aop.config.PrintlnLog;
import lhb.gdms.provider.user.domain.vo.LoginInfoVO;
import lhb.gdms.provider.user.domain.vo.LoginParamVO;
import lhb.gdms.provider.user.service.AdminLoginService;
import lhb.gdms.provider.user.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description  后台管理网站登录 controller
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/21
 * @time 10:24
 */
@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
public class AdminLoginController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RedisTokenStore tokenStore;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Autowired
    private AdminLoginService adminLoginService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 用户登录
     * @param loginParamVO
     * @return
     */
    @PostMapping("/user/login/admin")
    @PrintlnLog(description = "后台管理网站用户登录参数详情-Controller")
    public BaseResult adminLogin(@RequestBody LoginParamVO loginParamVO) {
        logger.debug(loginParamVO.toString());

        // 验证用户名和密码
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setSysUserUsername(loginParamVO.getUsername());
        SysUserEntity entity = sysUserService.selectOneByKeyWord(sysUserEntity);
        if (entity == null || !passwordEncoder.matches(loginParamVO.getPassword(), entity.getSysUserPassword())) {
            return BaseResult.error(HttpConstant.LOGIN_ERROR_MESSAGE);
        }
        return adminLoginService.adminLogin(loginParamVO);
    }

    /**
     * 获取用户信息，用于登录成功后跳转首页
     * @param authentication
     * @return
     */
    @GetMapping("/user/info/admin")
    public BaseResult adminInfo(Authentication authentication) {
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setSysUserUsername(authentication.getName());
        SysUserEntity entity = sysUserService.selectOneByKeyWord(sysUserEntity);
        logger.debug(entity.toString());
        LoginInfoVO loginInfoVO = new LoginInfoVO();
        loginInfoVO.setName(authentication.getName());
        loginInfoVO.setAvatar(entity.getSysUserIcon());
//        loginInfoVO.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        loginInfoVO.setRoles("USER");
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, loginInfoVO);
    }

    /**
     * 用户注销/退出
     * @return
     */
    @PostMapping("/user/logout/admin")
    public BaseResult adminLogout(HttpServletRequest request) {
        String access_token = request.getParameter("access_token");

        logger.debug(access_token);

        if (StringUtils.isNotBlank(access_token)) {
            logger.debug("删除token");
            tokenStore.removeAccessToken(tokenStore.readAccessToken(access_token));

        }
        return BaseResult.ok(HttpConstant.OK, HttpConstant.LOGOUT_OK_MESSAGE);
    }
}
