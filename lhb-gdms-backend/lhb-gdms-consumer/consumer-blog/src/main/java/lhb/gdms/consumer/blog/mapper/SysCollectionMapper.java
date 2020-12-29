package lhb.gdms.consumer.blog.mapper;

import lhb.gdms.commons.base.persistence.BaseMapper;
import lhb.gdms.commons.domain.entity.SysCollectionEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description  
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/27 
 * @time 17:34
 */
@Repository
public interface SysCollectionMapper extends BaseMapper<SysCollectionEntity> {

    /**
     * 校验收藏集名称是否重复-编辑
     * @param sysCollectionId
     * @param sysCollectionName
     * @param sysUserId
     * @return
     */
    String checkNameByIdToUpdate(@Param("sysCollectionId") Long sysCollectionId,
                         @Param("sysCollectionName") String sysCollectionName,
                         @Param("sysUserId") Long sysUserId);

    /**
     * 校验收藏集名称是否重复-新增
     * @param sysUserId
     * @param sysCollectionName
     * @return
     */
    String checkNameByIdToInsert(@Param("sysUserId") Long sysUserId,
                                 @Param("sysCollectionName") String sysCollectionName);

    /**
     * 查询列表全部信息
     * @param sysUserId
     * @return
     */
    List<SysCollectionEntity> getCollectionLists(@Param("sysUserId") Long sysUserId);

    /**
     * 根据id查询收藏集合下收藏的文章数量
     * @param sysCollectionId
     * @param sysUserId
     * @return
     */
    Integer getCollectionArticleCount(@Param("sysCollectionId") Long sysCollectionId,
                               @Param("sysUserId") Long sysUserId);
}
