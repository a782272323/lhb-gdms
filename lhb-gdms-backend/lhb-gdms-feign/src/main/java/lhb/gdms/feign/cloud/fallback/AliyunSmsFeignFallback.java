package lhb.gdms.feign.cloud.fallback;

import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.feign.cloud.AliyunSmsFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Description  
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/11 
 * @time 01:01
 */
@Component
public class AliyunSmsFeignFallback implements AliyunSmsFeign {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public BaseResult checkCode(String phone) throws Exception {
        logger.debug("阿里云短信服务,feign远程调用失败");
        return BaseResult.error(HttpConstant.SYSTEM_BUSY_MESSAGE);
    }

    @Override
    public BaseResult getCode(String phone, String code) {
        logger.debug("阿里云短信服务,feign远程调用失败");
        return BaseResult.error(HttpConstant.SYSTEM_BUSY_MESSAGE);
    }
}
