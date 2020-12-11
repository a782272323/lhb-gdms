package lhb.gdms.provider.cloud.service;

import lhb.gdms.commons.utils.BaseResult;

/**
 * @Description  qq邮件发送 service
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/9
 * @time 00:37
 */
public interface QQMailService {

    /**
     * 发送简单文本邮件
     * @param to
     * @param subject
     * @param content
     */
    public BaseResult sendSimpleMail(String to, String subject, String content);

    /**
     * 发送邮箱验证码
     * @param to
     * @return
     */
    public BaseResult sendMailCodeByQQ(String to);
}
