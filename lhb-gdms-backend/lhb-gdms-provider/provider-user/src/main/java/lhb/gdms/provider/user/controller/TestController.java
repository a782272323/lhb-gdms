package lhb.gdms.provider.user.controller;

import com.google.common.collect.Maps;
import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.domain.entity.SysUserEntity;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.dubbo.user.UserDubbo;
import lhb.gdms.feign.cloud.Oauth2Feign;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Map;

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

    @Autowired
    private Oauth2Feign oauth2Feign;

    @PostMapping("/web/user/test")
    public BaseResult test(HttpServletRequest request) {
        // 拼接参数，请求token
        Map<String, Object> paramsEmail = Maps.newHashMap();
        paramsEmail.put("grant_type", "email");
        paramsEmail.put("email", "1919470138@qq.com");
        paramsEmail.put("scope", "web-portal");
        paramsEmail.put("client_id", "portal");
        paramsEmail.put("client_secret", "123456");
        // 请求token
        BaseResult resultEmail = oauth2Feign.getToken(paramsEmail);
        return BaseResult.ok().put(200, "成功", "data", resultEmail);
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
