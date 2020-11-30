package lhb.gdms.feign.cloud.fallback;

import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.feign.cloud.QiniuFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/25
 * @time 12:59
 */
@Component
public class QiniuFeignFallback implements QiniuFeign {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public BaseResult uploadOne(MultipartFile file, String key) throws Exception {
        logger.debug("通用服务-七牛云,feign远程调用失败");
        return BaseResult.error("系统繁忙,请稍后重试");
    }

    @Override
    public BaseResult deleteOne(String key) throws Exception {
        logger.debug("通用服务-七牛云,feign远程调用失败");
        return BaseResult.error("系统繁忙,请稍后重试");
    }

    @Override
    public BaseResult test() {
        logger.debug("通用服务测试,feign远程调用失败");
        return BaseResult.error("系统繁忙,请稍后重试");
    }
}
