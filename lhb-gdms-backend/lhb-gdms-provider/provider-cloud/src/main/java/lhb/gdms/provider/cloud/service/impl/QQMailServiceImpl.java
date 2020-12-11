package lhb.gdms.provider.cloud.service.impl;

import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.commons.utils.RedisUtils;
import lhb.gdms.commons.utils.UUIDUtils;
import lhb.gdms.provider.cloud.service.QQMailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.Random;

/**
 * @Description  实现类
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/9
 * @time 00:37
 */
@Service
@Component
public class QQMailServiceImpl implements QQMailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private JavaMailSender mailSender;

    @Autowired
    TemplateEngine templateEngine;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 邮件发送人
     */
    @Value("${mail.fromMail.addr}")
    private String from;

    /**
     * 发送简单文本邮件
     * @param to 邮件接受者
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    @Override
    public BaseResult sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        // 设置邮件发送者
        mailMessage.setFrom(from);
        // 设置邮件接收者
        mailMessage.setTo(to);
        // 设置邮件主题
        mailMessage.setSubject(subject);
        // 设置邮件内容
        mailMessage.setText(content);

        try {
            mailSender.send(mailMessage);
            logger.debug("邮件发送成功");
            return BaseResult.ok(HttpConstant.MAIL_OK_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("邮件发送失败");
            return BaseResult.error(HttpConstant.MAIL_ERROR_MESSAGE);
        }
    }

    private BaseResult sendHtmlMail(String to, String subject, String path, Context context) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            // 设置邮件主题
            helper.setSubject(subject);
            // 设置邮件发送者
            helper.setFrom(from);
            // 设置邮件接收者
            helper.setTo(to);
            // 加载 thymeleaf 邮件模版
            String process = templateEngine.process(path, context);
            helper.setText(process, true);
            mailSender.send(mimeMessage);
            logger.debug("邮件发送成功");
            return BaseResult.ok(HttpConstant.MAIL_OK_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("邮件发送失败");
            return BaseResult.error(HttpConstant.MAIL_ERROR_MESSAGE);
        }
    }

    /**
     * 发送邮箱验证码
     * @param to
     * @return
     */
    @Override
    public BaseResult sendMailCodeByQQ(String to){
        String code = UUIDUtils.vCode();
        String key = "lhb:gdms:qq:mail:code:" + to;
        // 存入缓存保存5分钟
        logger.debug("存入缓存保存5分钟, code = " + code);
        redisUtils.set(key, code, 60 * 5);
        Context context = new Context();
        context.setVariable("code", code);
        return sendHtmlMail(to, "Bin 博客平台邮箱验证码", "code.html", context);
    }


}
