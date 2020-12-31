package lhb.gdms.consumer.blog.service.impl;

import lhb.gdms.commons.base.persistence.BaseServiceImpl;
import lhb.gdms.commons.domain.entity.SysCollectionEntity;
import lhb.gdms.consumer.blog.mapper.SysCollectionMapper;
import lhb.gdms.consumer.blog.service.SysCollectionService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/27
 * @time 17:35
 */
@Service
public class SysCollectionServiceImpl extends BaseServiceImpl<SysCollectionEntity, SysCollectionMapper> implements SysCollectionService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysCollectionMapper sysCollectionMapper;


    /**
     * 校验收藏集名称是否重复-编辑
     * @param sysCollectionId
     * @param sysCollectionName
     * @param sysUserId
     * @return true 存在 false 不重复
     */
    @Override
    public boolean checkNameByIdToUpdate(Long sysCollectionId, String sysCollectionName, Long sysUserId) {
        String isNull = sysCollectionMapper.checkNameByIdToUpdate(sysCollectionId, sysCollectionName, sysUserId);
        return StringUtils.isNotBlank(isNull) ? true : false;
    }

    /**
     * 校验收藏集名称是否重复-新增
     * @param sysUserId
     * @param sysCollectionName
     * @return
     */
    @Override
    public boolean checkNameByIdToInsert(Long sysUserId, String sysCollectionName) {
        String isNull = sysCollectionMapper.checkNameByIdToInsert(sysUserId, sysCollectionName);
        return StringUtils.isNotBlank(isNull) ? true : false;
    }
}