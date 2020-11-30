package lhb.gdms.feign.cloud.fallback;

import lhb.gdms.commons.domain.dto.Oauth2DTO;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.feign.cloud.Oauth2Feign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description  认证服务远程调用
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/22
 * @time 00:29
 */
@Component
public class Oauth2FeignFallback implements Oauth2Feign {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取token
     * @param map
     * @return
     */
    @Override
    public BaseResult getToken(Map<String, Object> map) {
        logger.error("认证服务异常");
        return BaseResult.error("系统异常，请联系管理员");
    }
}
