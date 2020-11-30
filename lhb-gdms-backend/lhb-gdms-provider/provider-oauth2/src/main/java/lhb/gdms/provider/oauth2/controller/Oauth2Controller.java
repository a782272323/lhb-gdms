package lhb.gdms.provider.oauth2.controller;

import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.configuration.aop.config.PrintlnLog;
import lhb.gdms.feign.user.UserFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author Herbie Leung(梁鸿斌)
 * @Description
 * @date 2020/11/19
 * @time 23:41
 */
@RestController
public class Oauth2Controller {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取授权用户信息
     * 一定要配置，否则别的服务认证会失败
     *
     * @param principal
     * @return
     */
    @PrintlnLog(description = "根据token来获取用户信息-controller")
    @GetMapping("/oauth2/info")
    public Principal user(Principal principal) {
        logger.debug(principal.toString());
        System.out.println(principal.toString());
        return principal;
    }

    /**
     * 测试绕过token校验
     * @return
     */
    @GetMapping("/web/test")
    public BaseResult test() {
        return BaseResult.ok("测试绕过token成功");

    }


}
