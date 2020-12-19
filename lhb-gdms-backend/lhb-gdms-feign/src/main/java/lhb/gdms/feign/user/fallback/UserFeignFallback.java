package lhb.gdms.feign.user.fallback;

import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.domain.entity.SysUserEntity;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.feign.user.UserFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Description  用户服务feign调用异常处理类/降级/熔断
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
        return BaseResult.error(HttpConstant.SYSTEM_BUSY_MESSAGE);
    }

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    @Override
    public BaseResult selectOneByUsername(String username) {
        logger.debug("管理网站用户服务feign远程调用失败");
        return BaseResult.error(HttpConstant.SYSTEM_BUSY_MESSAGE);
    }

    @Override
    public BaseResult updateUserSysIcon(SysUserEntity entity) {
        logger.debug("管理网站用户服务feign远程调用失败");
        return BaseResult.error(HttpConstant.SYSTEM_BUSY_MESSAGE);
    }

    @Override
    public BaseResult getList(Long id) {
        logger.debug("管理网站用户服务feign远程调用失败");
        return BaseResult.error(HttpConstant.SYSTEM_BUSY_MESSAGE);
    }

    @Override
    public BaseResult updateUserBaseInfo(SysUserEntity entity) {
        logger.debug("管理网站用户服务feign远程调用失败");
        return BaseResult.error(HttpConstant.SYSTEM_BUSY_MESSAGE);
    }
}
