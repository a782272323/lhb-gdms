package lhb.gdms.feign.cloud;

import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.configuration.aop.config.PrintlnLog;
import lhb.gdms.feign.cloud.fallback.QQMailFeignFallback;
import lhb.gdms.feign.cloud.fallback.QiniuFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/9
 * @time 10:51
 */
@Component
@FeignClient(name = "provider-cloud", fallback = QQMailFeignFallback.class)
public interface QQMailFeign {

    /**
     * 通过qq邮箱发送验证码
     * @param to 接收者邮箱
     * @return
     */
    @PostMapping("/web/cloud/qq/mail/code")
    public BaseResult sendCodeMailByQQ(@RequestParam("to") String to);

    /**
     * 校验邮箱短信验证码
     * @param to
     * @param code
     * @return
     */
    @GetMapping("/web/cloud/qq/mail/code")
    public BaseResult checkCodeMailByQQ(@RequestParam("to") String to,
                                        @RequestParam("code") String code);
}
