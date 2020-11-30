package lhb.gdms.consumer.admin.mapper;

import lhb.gdms.commons.base.persistence.BaseMapper;
import lhb.gdms.commons.domain.entity.SysUserIconEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/25
 * @time 15:08
 */
@Repository
public interface SysUserIconMapper extends BaseMapper<SysUserIconEntity> {

    /**
     * 清空默认头像设置
     */
    void clearDefaultIcon();

    /**
     * 更换默认头像
     * @param sysUserIconId
     * @param updated
     * @return
     */
    Integer updateDefaultIcon(@Param("sysUserIconId") Long sysUserIconId,@Param("updated") Date updated);
}
