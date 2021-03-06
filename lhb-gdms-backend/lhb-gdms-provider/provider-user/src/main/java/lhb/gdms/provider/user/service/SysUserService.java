package lhb.gdms.provider.user.service;

import lhb.gdms.commons.base.persistence.BaseService;
import lhb.gdms.commons.base.persistence.BaseServiceImpl;
import lhb.gdms.commons.domain.entity.SysUserEntity;
import lhb.gdms.provider.user.mapper.SysUserMapper;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/21
 * @time 23:08
 */
public interface SysUserService extends BaseService<SysUserEntity> {

    /**
     * 修改用户头像信息
     * @param entity
     * @return
     */
    public Integer updateUserSysIcon(SysUserEntity entity);

    /**
     * 修改用户基础信息
     * @param entity
     * @return
     */
    public Integer updateUserBaseInfo(SysUserEntity entity);
}
