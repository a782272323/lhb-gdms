package lhb.gdms.consumer.admin.service;

import lhb.gdms.commons.base.persistence.BaseService;
import lhb.gdms.commons.domain.entity.SysUserIconEntity;

/**
 * @Description  
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/25 
 * @time 16:44
 */
public interface SysUserIconService extends BaseService<SysUserIconEntity> {

    /**
     * 更换默认头像
     * @param sysUserIconId
     * @return
     */
    Integer updateDefaultIcon(Long sysUserIconId);
}
