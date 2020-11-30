package lhb.gdms.consumer.admin.service.impl;

import lhb.gdms.commons.base.persistence.BaseServiceImpl;
import lhb.gdms.commons.domain.entity.SysUserIconEntity;
import lhb.gdms.consumer.admin.mapper.SysUserIconMapper;
import lhb.gdms.consumer.admin.service.SysUserIconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/25
 * @time 15:24
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SysUserIconServiceImpl extends BaseServiceImpl<SysUserIconEntity, SysUserIconMapper> implements SysUserIconService {

    @Autowired
    private SysUserIconMapper sysUserIconMapper;

    /**
     * 更换默认头像
     * @param sysUserIconId
     * @return
     */
    @Override
    public Integer updateDefaultIcon(Long sysUserIconId) {
        sysUserIconMapper.clearDefaultIcon();
        return sysUserIconMapper.updateDefaultIcon(sysUserIconId, new Date());
    }
}
