package lhb.gdms.provider.user.controller;

import com.google.common.collect.Maps;
import io.lettuce.core.RedisURI;
import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.constant.ResponseConstant;
import lhb.gdms.commons.domain.entity.SysUserEntity;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.commons.utils.RedisUtils;
import lhb.gdms.configuration.aop.config.PrintlnLog;
import lhb.gdms.configuration.utils.SecurityOauth2Utils;
import lhb.gdms.provider.user.domain.vo.LoginInfoVO;
import lhb.gdms.provider.user.domain.vo.LoginParamVO;
import lhb.gdms.provider.user.mapper.SysUserMapper;
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
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

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
    private RedisUtils redisUtils;

    @Autowired
    private AdminLoginService adminLoginService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SecurityOauth2Utils securityOauth2Utils;

    @Autowired
    private SysUserMapper sysUserMapper;

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
    public BaseResult adminInfo(Authentication authentication) throws Exception{
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setSysUserId(sysUserId);
        SysUserEntity entity = sysUserMapper.getListById(sysUserId);
        LoginInfoVO vo = new LoginInfoVO();
        vo.setName(entity.getSysUserUsername());
        vo.setAvatar(entity.getSysUserIcon());
        vo.setRoles("System Admin");
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, vo);
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
            logger.debug(tokenStore.readAccessToken(access_token).getValue());
            tokenStore.removeAccessToken(tokenStore.readAccessToken(access_token));

        }
        return BaseResult.ok(HttpConstant.OK, HttpConstant.LOGOUT_OK_MESSAGE);
    }

    /**
     * todo 测试
     * @param request
     * @param principal
     * @return
     */
    @GetMapping("/user/token")
    public BaseResult testToken(HttpServletRequest request, Principal principal) {
        String access_token = request.getParameter("access_token");
        logger.debug(access_token);

        String accessKey = "access:" + access_token;
        String accessToRefreshKey = "access_to_refresh:" + access_token;
        String authKey = "auth:" + access_token;
        String clientIdToAccessKey = "client_id_to_access:" + "admin";
        String unameToAccessKey = "uname_to_access:" + principal.getName();

        Map<String, Object> map = Maps.newHashMap();
        map.put("access", redisUtils.getValueByKey(accessKey));
        map.put("access_to_refresh", redisUtils.getValueByKey(accessToRefreshKey));
        map.put("auth", redisUtils.getValueByKey(authKey));
        map.put("client_id_to_access", redisUtils.getValueByKey(clientIdToAccessKey));
        map.put("uname_to_access", redisUtils.getValueByKey(unameToAccessKey));
        logger.debug(map.toString());

//        redisUtils.deleteCache(accessKey);
//        redisUtils.deleteCache(accessToRefreshKey);
//        redisUtils.deleteCache(authKey);
//        redisUtils.deleteCache(clientIdToAccessKey);
//        redisUtils.deleteCache(unameToAccessKey);

//        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, "data", map);
//        if (redisUtils.exists(accessKey)) {
//            return null;
//        }
        return BaseResult.ok();
    }
}
