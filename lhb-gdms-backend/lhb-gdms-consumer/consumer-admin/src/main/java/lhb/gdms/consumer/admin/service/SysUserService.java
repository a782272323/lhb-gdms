package lhb.gdms.consumer.admin.service;

import lhb.gdms.commons.base.persistence.BaseService;
import lhb.gdms.commons.domain.entity.SysUserEntity;
import org.springframework.context.annotation.Primary;

import java.util.List;

public interface SysUserService extends BaseService<SysUserEntity> {
//public interface SysUserService {

    /**
     * 根据七牛云key查找是否有用户使用系统头像
     * @param entity
     * @return
     */
    public List<SysUserEntity> findIconByKey(SysUserEntity entity);
}
