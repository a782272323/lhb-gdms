package lhb.gdms.provider.cloud.controller;

import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.commons.utils.RedisUtils;
import lhb.gdms.commons.utils.RegularExpressionUtil;
import lhb.gdms.configuration.aop.config.PrintlnLog;
import lhb.gdms.provider.cloud.service.QQMailService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

/**
 * @author Herbie Leung(梁鸿斌)
 * @Description qq邮箱发送邮件
 * @date 2020/12/9
 * @time 00:27
 */
@RestController
@CrossOrigin
public class QQMailController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QQMailService qqMailService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 发送文本邮件
     *
     * @param to
     * @param subject
     * @param text
     * @return
     */
    @PostMapping("/web/cloud/qq/mail/simpleText")
    public BaseResult sendSimpleMail(@RequestParam("to") String to,
                                     @RequestParam("subject") String subject,
                                     @RequestParam("text") String text) {
        return qqMailService.sendSimpleMail(to, subject, text);
    }

    /**
     * 通过qq邮箱发送验证码
     * @param to 接收者邮箱
     * @return
     */
    @PrintlnLog(description = "通过qq邮箱发送验证码-controller")
    @PostMapping("/web/cloud/qq/mail/code")
    public BaseResult sendCodeMailByQQ(@RequestParam("to") String to) {
        // 邮箱不能为空
        if (StringUtils.isBlank(to)) {
            return BaseResult.error(RegularExpressionUtil.REGEX_EMAIL_NULL);
        }
        // 邮箱格式错误
        if (!RegularExpressionUtil.isEmail(to)) {
            return BaseResult.error(RegularExpressionUtil.REGEX_EMAIL_ERROR);
        }
        return qqMailService.sendMailCodeByQQ(to);
    }

    /**
     * 校验邮箱短信验证码
     * @param to
     * @param code
     * @return
     */
    @PrintlnLog(description = "校验邮箱短信验证码")
    @GetMapping("/web/cloud/qq/mail/code")
    public BaseResult checkCodeMailByQQ(@RequestParam("to") String to,
                                        @RequestParam("code") String code) {
        String key = "lhb:gdms:qq:mail:code:" + to;
        String checkCode = (String) redisUtils.getValueByKey(key);
        if (StringUtils.isNotBlank(checkCode) && checkCode.equals(code)) {
            redisUtils.deleteCache(key);
            return BaseResult.ok(HttpConstant.CODE_OK_MESSAGE);
        }
        return BaseResult.error(HttpConstant.INVALID_CODE_MESSAGE);
    }

}
