package lhb.gdms.consumer.blog.mapper;

import lhb.gdms.commons.base.persistence.BaseMapper;
import lhb.gdms.commons.domain.entity.SysCollectionEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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
     * 根据id查看单条收藏集列表
     * @param sysUserId
     * @param sysCollectionId
     * @return
     */
    List<Map<String, Object>> getCollectionListById(@Param("sysUserId") Long sysUserId,
                                                    @Param("sysCollectionId") Long sysCollectionId);

    /**
     * 查询当前用户的收藏集并且用于文章详情判断当前文章是否被收藏
     * @param sysUserId
     * @return
     */
    List<Map<String, Object>> getCollectionListsBySysUserId(@Param("sysUserId") Long sysUserId);

    /**
     * 获取收藏集下的文章数量
     * @param sysCollectionId
     * @return
     */
    Integer getCollectionArticleCount(@Param("sysCollectionId") Long sysCollectionId);

    /**
     * 根据用户id查询用户创建的收藏集总数
     * @param sysUserId
     * @return
     */
    Integer getHomeUserCollectionCountAll(@Param("sysUserId") Long sysUserId);
}
