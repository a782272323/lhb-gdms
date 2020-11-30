package lhb.gdms.feign.user;

import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.feign.user.fallback.UserFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description 用户服务feign远程调用
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/20
 * @time 15:51
 */
@Component
@FeignClient(name = "provider-user", fallback = UserFeignFallback.class)
public interface UserFeign {

    /**
     * 测试
     * @return
     */
    @GetMapping("/user/feign")
    public BaseResult test();

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    @GetMapping("/user/selectOne/username")
    public BaseResult selectOneByUsername(@RequestParam String username);
}
