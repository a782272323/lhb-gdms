package lhb.gdms.consumer.blog.service;

import lhb.gdms.commons.base.persistence.BaseService;
import lhb.gdms.commons.domain.entity.SysUserEntity;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/18
 * @time 15:24
 */
public interface SysUserService extends BaseService<SysUserEntity> {

    /**
     * 修改用户基础信息
     * @param entity
     * @return
     */
    public Integer updateUserBaseInfo(SysUserEntity entity);
}
