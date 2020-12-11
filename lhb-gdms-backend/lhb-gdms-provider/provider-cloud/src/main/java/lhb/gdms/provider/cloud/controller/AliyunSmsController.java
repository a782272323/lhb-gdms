package lhb.gdms.provider.cloud.controller;

import com.google.common.collect.Maps;
import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.utils.*;
import lhb.gdms.configuration.aop.config.PrintlnLog;
import lhb.gdms.provider.cloud.service.AliyunSmsService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Random;

/**
 * @author Herbie Leung(梁鸿斌)
 * @Description
 * @date 2020/12/10
 * @time 13:03
 */
@RestController
@CrossOrigin
public class AliyunSmsController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AliyunSmsService aliyunSmsService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 短信验证模版code
     */
    @Value("${aliyun.sms.templateCode01}")
    private String templateCode01;

    /**
     * 阿里云发送验证码短信
     * @param phone
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "阿里云发送验证码短信-controller")
    @PostMapping("/web/cloud/aliyun/sms/checkCode")
    public BaseResult checkCode(@RequestParam("phone") String phone) throws Exception{
        String code = UUIDUtils.vCode();
        Map<String,Object> map = Maps.newHashMap();
        map.put("code", code);
        logger.debug("templateCode01 = " + templateCode01.toString() + ",code = " + code);

        // 校验手机号
        if (StringUtils.isBlank(phone)) {
            return BaseResult.error(RegularExpressionUtil.REGEX_MOBILE_NULL);
        }
        if (!RegularExpressionUtil.isMobile(phone)) {
            return BaseResult.error(RegularExpressionUtil.REGEX_MOBILE_ERROR);
        }
        return aliyunSmsService.sendCheckCodeSms(phone);
    }

    /**
     * 校验短信验证码
     * @param phone
     * @return
     */
    @GetMapping("/web/cloud/aliyun/sms/getCode")
    public BaseResult getCode(@RequestParam("phone") String phone,
                              @RequestParam("code") String code) {
        String key = "lhb:gdms:aliyun:sms:code:" + phone;
        String codeRedis =(String) redisUtils.getValueByKey(key);
        if (StringUtils.isNotBlank(codeRedis) && codeRedis.equals(code)) {
            redisUtils.deleteCache(key);
            return BaseResult.ok("校验成功");
        }
        return BaseResult.error("验证码失效，请重新获取");
    }
}
