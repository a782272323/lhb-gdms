package lhb.gdms.feign.cloud.fallback;

import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.feign.cloud.QQMailFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/9
 * @time 10:51
 */
@Component
public class QQMailFeignFallback implements QQMailFeign {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public BaseResult sendCodeMailByQQ(String to) {
        logger.debug("qq邮件服务,feign远程调用失败");
        return BaseResult.error(HttpConstant.SYSTEM_BUSY_MESSAGE);
    }

    @Override
    public BaseResult checkCodeMailByQQ(String to, String code) {
        logger.debug("qq邮件服务,feign远程调用失败");
        return BaseResult.error(HttpConstant.SYSTEM_BUSY_MESSAGE);
    }
}
