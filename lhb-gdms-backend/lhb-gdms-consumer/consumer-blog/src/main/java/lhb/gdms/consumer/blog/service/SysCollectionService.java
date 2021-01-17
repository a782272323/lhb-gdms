package lhb.gdms.consumer.blog.service;

import lhb.gdms.commons.base.persistence.BaseService;
import lhb.gdms.commons.domain.entity.SysCollectionEntity;
import org.apache.ibatis.annotations.Param;

/**
 * @Description  
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/27 
 * @time 17:34
 */
public interface SysCollectionService extends BaseService<SysCollectionEntity> {

    /**
     * 校验收藏集名称是否重复-编辑
     * @param sysCollectionId
     * @param sysCollectionName
     * @param sysUserId
     * @return
     */
    public boolean checkNameByIdToUpdate(Long sysCollectionId,
                                 String sysCollectionName,
                                 Long sysUserId);

    /**
     * 校验收藏集名称是否重复-新增
     * @param sysUserId
     * @param sysCollectionName
     * @return
     */
    public boolean checkNameByIdToInsert(Long sysUserId, String sysCollectionName);

    /**
     * 判断当前文章是否存在于当前收藏集
     * @param sysCollectionId
     * @param articleId
     * @return
     */
    public Boolean checkArticleCollection(Long sysCollectionId, Long articleId);
}
