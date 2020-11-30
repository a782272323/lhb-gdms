package lhb.gdms.feign.cloud;

import lhb.gdms.commons.domain.dto.Oauth2DTO;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.feign.cloud.fallback.Oauth2FeignFallback;
import lhb.gdms.feign.user.fallback.UserFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author Herbie Leung(梁鸿斌)
 * @Description
 * @date 2020/11/22
 * @time 00:27
 */
@Component
@FeignClient(name = "provider-oauth2", fallback = Oauth2FeignFallback.class)
public interface Oauth2Feign {

    /**
     * 获取token
     * @param map
     * @return
     */
    @PostMapping("/oauth/token")
    public BaseResult getToken(@RequestParam Map<String, Object> map);

}
