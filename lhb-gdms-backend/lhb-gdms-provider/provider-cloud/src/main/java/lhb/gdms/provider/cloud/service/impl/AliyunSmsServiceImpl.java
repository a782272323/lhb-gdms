package lhb.gdms.provider.cloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.google.common.collect.Maps;
import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.commons.utils.RedisUtils;
import lhb.gdms.commons.utils.UUIDUtils;
import lhb.gdms.provider.cloud.config.AliyunSMSConfig;
import lhb.gdms.provider.cloud.service.AliyunSmsService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/10
 * @time 12:42
 */
@Service
public class AliyunSmsServiceImpl implements AliyunSmsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AliyunSMSConfig aliyunSMSConfig;

    @Autowired
    private RedisUtils redisUtils;


    /**
     * 发送验证码短信
     * @param phone
     * @return
     */
    @Override
    public BaseResult sendCheckCodeSms(String phone) {

        // 指定地域名称
        DefaultProfile profile = DefaultProfile.getProfile(aliyunSMSConfig.getRegionId(),aliyunSMSConfig.getAccessKeyId(), aliyunSMSConfig.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);

        logger.debug("aliyunSMSConfig = " + aliyunSMSConfig.toString());
        // 创建通用短信请求对象
        CommonRequest request = new CommonRequest();
        // 指定请求方式
        request.setSysMethod(MethodType.POST);
        // 指定域名名称
        request.setSysDomain(aliyunSMSConfig.getDomain());
        // 签名算法版本 固定
        request.setSysVersion("2017-05-25");
        // 请求api的名称
        request.setSysAction(aliyunSMSConfig.getAction());
        // 指定域名名称
        request.putQueryParameter("RegionId", aliyunSMSConfig.getRegionId());
        // 指定手机号
        request.putQueryParameter("PhoneNumbers", phone);
        // 指定签名
        request.putQueryParameter("SignName", aliyunSMSConfig.getSignName());
        // 指定签名模版
        request.putQueryParameter("TemplateCode", aliyunSMSConfig.getTemplateCode01());

        // 这里的code就是后台自动生成的code
        String code = UUIDUtils.vCode();
        Map<String, Object> params = Maps.newHashMap();
        params.put("code", code);
        // 短信验证码存入redis,存入缓存保存5分钟
        String key = "lhb:gdms:aliyun:sms:code:" + phone;
        logger.debug("存入缓存保存5分钟, code = " + code);
        redisUtils.set(key, code, 60 * 5);
        // 放入参数，转化为json字符串
        request.putQueryParameter("TemplateParam", JSON.toJSONString(params));

        try {
            CommonResponse response = client.getCommonResponse(request);
            logger.debug("response = " + response);
            logger.debug(JSON.parseObject(response.getData(), Map.class).get("Message").toString());
            return BaseResult.ok(HttpConstant.SMS_OK_MESSAGE);
        } catch (ServerException e) {
            e.printStackTrace();
            // 删除缓存中的key
            logger.debug("阿里云短信服务接口异常ServerException，移除code缓存");
            redisUtils.deleteCache(key);
            return BaseResult.error(HttpConstant.SMS_ERROR_MESSAGE);
        } catch (ClientException e) {
            e.printStackTrace();
            // 删除缓存中的key
            logger.debug("阿里云短信服务接口异常ClientException，移除code缓存");
            redisUtils.deleteCache(key);
            return BaseResult.error(HttpConstant.SMS_ERROR_MESSAGE);
        }
    }

//    /**
//     * 添加一个自然手机号接收验证码限制
//     * 一个自然日只能接收10次验证码短信
//     * @param phone
//     * @return
//     */
//    private Integer addPhoneLimit(String phone) {
//        String phoneKey = "lhb:gdms:aliyun:sms:code:" + phone;
//        String limitKey = "lhb:gdms:aliyun:sms:limit:" + phone;
//        Integer limit = null;
//        if (StringUtils.isBlank((String) redisUtils.getValueByKey(phoneKey))) {
//            limit = 1;
//            redisUtils.set(limitKey, limit, 60 * 60 * 24);
//        }
//        Integer add = (Integer) redisUtils.getValueByKey(limitKey);
//        if (add > 0) {
//            limit += 1;
//        }
//        logger.debug("当前手机号{},在一个自然内使用的短信次数为{}次",phone,limit);
//        return limit;
//    }

}
