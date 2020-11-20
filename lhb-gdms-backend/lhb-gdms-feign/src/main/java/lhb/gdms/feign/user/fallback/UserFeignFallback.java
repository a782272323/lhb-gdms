package lhb.gdms.feign.user.fallback;

import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.feign.user.UserFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Description  用户服务feign调用异常处理类/降级
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/20
 * @time 15:53
 */
@Component
public class UserFeignFallback implements UserFeign {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public BaseResult test() {
        logger.debug("管理网站用户服务feign远程调用失败");
        return BaseResult.error("系统繁忙,请稍后重试");
    }
}
