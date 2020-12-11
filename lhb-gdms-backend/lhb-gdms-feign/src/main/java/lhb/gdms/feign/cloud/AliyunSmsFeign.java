package lhb.gdms.feign.cloud;


import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.configuration.aop.config.PrintlnLog;
import lhb.gdms.feign.cloud.fallback.AliyunSmsFeignFallback;
import lhb.gdms.feign.cloud.fallback.QQMailFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/11
 * @time 00:59
 */
@Component
@FeignClient(name = "provider-cloud", fallback = AliyunSmsFeignFallback.class)
public interface AliyunSmsFeign {

    /**
     * 阿里云发送验证码短信
     * @param phone
     * @return
     * @throws Exception
     */
    @PostMapping("/web/cloud/aliyun/sms/checkCode")
    public BaseResult checkCode(@RequestParam("phone") String phone) throws Exception;

    /**
     * 校验短信验证码
     * @param phone
     * @return
     */
    @GetMapping("/web/cloud/aliyun/sms/getCode")
    public BaseResult getCode(@RequestParam("phone") String phone,
                              @RequestParam("code") String code);
}
