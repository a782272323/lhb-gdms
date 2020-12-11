package lhb.gdms.provider.cloud.service;

import lhb.gdms.commons.utils.BaseResult;

import java.util.Base64;

/**
 * @Description  阿里云短信服务 service
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/10
 * @time 12:29
 */
public interface AliyunSmsService {

    /**
     * 发送验证码短信
     * @param phone
     * @return
     */
    public BaseResult sendCheckCodeSms(String phone);

}
