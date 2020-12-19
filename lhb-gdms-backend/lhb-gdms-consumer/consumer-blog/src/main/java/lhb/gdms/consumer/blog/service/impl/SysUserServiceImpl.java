package lhb.gdms.consumer.blog.service.impl;

import lhb.gdms.commons.base.persistence.BaseServiceImpl;
import lhb.gdms.commons.domain.entity.SysUserEntity;
import lhb.gdms.consumer.blog.mapper.SysUserMapper;
import lhb.gdms.consumer.blog.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/18
 * @time 15:24
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserEntity, SysUserMapper> implements SysUserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    String introduction = "该用户很懒，没有写简介";

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 修改用户基础信息
     * @param entity
     * @return
     */
    @Override
    public Integer updateUserBaseInfo(SysUserEntity entity) {
        SysUserEntity sysUserEntity = sysUserMapper.selectOneById(entity);
        if (sysUserEntity.getSysUserIntroduction().trim().equals(introduction) == true && StringUtils.isNotBlank(sysUserEntity.getSysUserIntroduction())) {
            entity.setSysUserIntroduction(introduction);
        }
        entity.setUpdated(new Date());
        return sysUserMapper.update(entity);
    }

}
