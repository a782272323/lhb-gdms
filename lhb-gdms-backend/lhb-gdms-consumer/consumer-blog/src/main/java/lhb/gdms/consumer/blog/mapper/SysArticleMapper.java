package lhb.gdms.consumer.blog.mapper;

import lhb.gdms.commons.base.persistence.BaseMapper;
import lhb.gdms.commons.domain.entity.SysArticleEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/25
 * @time 17:16
 */
@Repository
public interface SysArticleMapper extends BaseMapper<SysArticleEntity> {

    /**
     * 根据id查看单个文章详情
     * @param articleId
     * @return
     */
    List<SysArticleEntity> getArticleListById(@Param("articleId") Long articleId);

    /**
     * 新增且返回主键id
     * @param entity
     * @return
     */
    Integer insertData(@Param("entity") SysArticleEntity entity);

    /**
     * 查询文章相关详情用于收藏集模块
     * @param articleId
     * @return
     */
    List<Map<String, Object>> getArticlesDetailsToCollection(@Param("articleId") Long articleId);

    /**
     * 查询该标签下有多少文章
     * @param labelId
     * @return
     */
    Integer getLabelArticleCount(@Param("labelId") Long labelId);

    /**
     * 根据标签id查看文章详情
     * @param labelId
     * @return
     */
    List<Map<String, Object>> getArticleDetailsByLabelId(@Param("labelId") Long labelId);

    /**
     * 文章被点击查看时被阅读数加
     * @param articleId
     * @return
     */
    Integer insertArticleBrowse(@Param("articleId") Long articleId);

    /**
     * 根据文章id查询文章详情
     * @param articleId
     * @return
     */
    List<SysArticleEntity> getArticleDetailsByArticleId(@Param("articleId") Long articleId);

    /**
     * 根据文章id获取文章详情信息
     * @param articleId
     * @return
     */
    SysArticleEntity getArticleInfoByArticleId(@Param("articleId") Long articleId);

    /**
     * 根据用户id查询所发表的文章
     * @param sysUserId
     * @return
     */
    List<Map<String, Object>> getArticleBySysUserId(@Param("sysUserId") Long sysUserId);

}
