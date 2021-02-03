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
     * 根据文章id查看单个文章详情
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

    /**
     * 按照首页文章发布时间降序排序-推荐
     * @return
     */
    List<Map<String, Object>> getHomeArticleLists();

    /**
     * 根据标签id查看首页文章详情，按照时间降序排序-动态标签
     * @return
     */
    List<Map<String, Object>> getHomeArticleListsByLabelId(@Param("labelId") Long labelId);

    /**
     * 根据用户id查询文章总数
     * @param sysUserId
     * @return
     */
    Integer getHomeUserArticleCount(@Param("sysUserId") Long sysUserId);

    /**
     * 用户主页评论列表
     * @param userId
     * @return
     */
    List<Map<String, Object>> getHomeCommentsLists(@Param("userId") Long userId);

    /**
     * 搜索文章
     * @param search
     * @param articleType
     * @return
     */
    List<Map<String, Object>> searchArticleLists(@Param("search") String search,
                                                 @Param("articleType") String articleType);

    /**
     * 搜索文章内容根据文章id
     * @param articleId
     * @return
     */
    String searchArticleContent(@Param("articleId") Long articleId);
}
