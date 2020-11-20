package lhb.gdms.feign.user;

import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.feign.user.fallback.UserFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/20
 * @time 15:51
 */
@Component
@FeignClient(name = "provider-user", fallback = UserFeignFallback.class)
public interface UserFeign {

    @GetMapping("/user/feign")
    public BaseResult test();
}
