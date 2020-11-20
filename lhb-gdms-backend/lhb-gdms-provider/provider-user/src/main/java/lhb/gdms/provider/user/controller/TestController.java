package lhb.gdms.provider.user.controller;

import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.dubbo.user.UserDubbo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {

    @Resource
    private UserDubbo userDubbo;

    @GetMapping("/user/test")
    public BaseResult test() {
        return BaseResult.ok("测试链接");
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
        return BaseResult.ok("feign远程调用成功");
    }
}
