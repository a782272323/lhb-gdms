package lhb.gdms.consumer.blog.service.impl;

import lhb.gdms.commons.base.persistence.BaseServiceImpl;
import lhb.gdms.commons.domain.entity.SysCollectionEntity;
import lhb.gdms.consumer.blog.mapper.OtherMapper;
import lhb.gdms.consumer.blog.mapper.SysCollectionMapper;
import lhb.gdms.consumer.blog.service.SysCollectionService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    @Autowired
    private OtherMapper otherMapper;


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

    /**
     * 判断当前文章是否存在于当前收藏集
     * @param sysCollectionId
     * @param articleId
     * @return
     */
    @Override
    public Boolean checkArticleCollection(Long sysCollectionId, Long articleId) {
        List<Map<String, Object>> list = otherMapper.getArticleCollectionInfoByTwoId(articleId, sysCollectionId);
        return list != null && list.size() > 0 ? true : false;
    }

    /**
     * 根据用户id查询用户创建的收藏集总数
     * @param sysUserId
     * @return
     */
    @Override
    public Integer getHomeUserCollectionCountAll(Long sysUserId) {
        return sysCollectionMapper.getHomeUserCollectionCountAll(sysUserId);
    }
}
