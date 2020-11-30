package lhb.gdms.provider.user.controller;

import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.domain.entity.SysUserEntity;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.dubbo.user.UserDubbo;
import lhb.gdms.feign.cloud.QiniuFeign;
import lhb.gdms.provider.user.mapper.SysUserMapper;
import lhb.gdms.provider.user.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;

@RestController
public class TestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private UserDubbo userDubbo;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTokenStore tokenStore;

    @Autowired
    private QiniuFeign qiniuFeign;

    @GetMapping("/web/user/test")
    public BaseResult test(HttpServletRequest request) {
        String access_token = request.getParameter("access_token");

        logger.debug(access_token);


        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(access_token);
        logger.debug("查看token相关信息 = " + oAuth2AccessToken.toString());
        return BaseResult.ok(HttpConstant.OK, HttpConstant.LOGOUT_OK_MESSAGE);
    }

    /**
     * 测试dubbo
     * @return
     */
    @GetMapping("/user/dubbo")
    public BaseResult dubbo() {
        return userDubbo.echo("远程测试");
    }

    /**
     * 测试feign远程调用
     * @return
     */
    @GetMapping("/user/feign")
    public BaseResult feign() {
        return qiniuFeign.test();
    }
}
