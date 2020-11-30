package lhb.gdms.consumer.admin.service.impl;

import lhb.gdms.commons.base.persistence.BaseServiceImpl;
import lhb.gdms.commons.domain.entity.SysUserEntity;
import lhb.gdms.consumer.admin.mapper.SysUserMapper;
import lhb.gdms.consumer.admin.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/25
 * @time 20:35
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserEntity, SysUserMapper> implements SysUserService {


    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 根据七牛云key查找是否有用户使用系统头像
     * @param entity
     * @return
     */
    @Override
    public List<SysUserEntity> findIconByKey(SysUserEntity entity) {
        return sysUserMapper.findIconByKey(entity);
    }
}
