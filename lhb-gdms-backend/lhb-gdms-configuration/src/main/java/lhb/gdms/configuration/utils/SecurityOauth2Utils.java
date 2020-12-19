package lhb.gdms.configuration.utils;

import lhb.gdms.commons.domain.entity.SysUserEntity;
import lhb.gdms.commons.utils.MapperUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


/**
 * @Description  security 工具类
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/19
 * @time 14:31
 */
@Component
public class SecurityOauth2Utils {

    /**
     * 从token获取用户的userId
     * @param authentication
     * @return
     * @throws Exception
     */
    public Long getUserId(Authentication authentication) throws Exception{
        SysUserEntity sysUserEntity = MapperUtils.json2pojo(authentication.getName(), SysUserEntity.class);
        return sysUserEntity.getSysUserId();
    }

    /**
     * 从token获取用户信息详情
     * @param authentication
     * @return
     * @throws Exception
     */
    public SysUserEntity getEntity(Authentication authentication) throws Exception {
        SysUserEntity sysUserEntity = MapperUtils.json2pojo(authentication.getName(), SysUserEntity.class);
        return sysUserEntity;
    }
}
