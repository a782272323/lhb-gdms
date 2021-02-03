package lhb.gdms.consumer.blog.service;

import lhb.gdms.commons.base.persistence.BaseService;
import lhb.gdms.commons.domain.entity.SysArticleEntity;

import java.util.List;
import java.util.Map;

/**
 * @Description  
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/25 
 * @time 13:19
 */
public interface SysArticleService extends BaseService<SysArticleEntity> {

    /**
     * 新增
     * @param entity
     * @return 返回主键id
     */
    public Long insertData(SysArticleEntity entity);

    /**
     * 查询该标签下有多少文章
     * @param labelId
     * @return
     */
    public Integer getLabelArticleCount(Long labelId);

    /**
     * 查询用户所有获得的点赞总数
     * @param sysUserId
     * @return
     */
    public Integer getArticlePriseAll(Long sysUserId);

    /**
     * 查询用户所有被阅读数
     * @param sysUserId
     * @return
     */
    public Integer getArticleBrowseAll(Long sysUserId);

    /**
     * 按照最新首页文章发布时间降序排序-推荐
     * @param userId 当前登录用户id
     * @return
     */
    public List<Map<String, Object>> getHomeArticleLists(Long userId);

    /**
     * 按照最热门首页文章来排序-推荐
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getHomeArticlesListsByHostest(Long userId);

    /**
     * 按照最新首页文章发布时间降序排序-动态标签
     * @param labelId
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getHomeArticlesListsByLabelId(Long labelId, Long userId);

    /**
     * 按照最热门首页文章来排序-动态标签
     * @param labelId
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getHomeArticlesListsByLabelIdByHostest(Long labelId, Long userId);

    /**
     * 根据用户id查询文章总数
     * @param sysUserId
     * @return
     */
    public Integer getHomeUserArticleCount(Long sysUserId);

    /**
     * 根据用户id查询所发表的文章
     * @param sysUserId
     * @return
     */
    public List<Map<String, Object>> getArticleBySysUserId(Long sysUserId);

    /**
     * 用户主页评论列表
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getHomeCommentsLists(Long userId);

    /**
     * 搜索文章
     * @param search
     * @param articleType
     * @return
     */
    public List<Map<String, Object>> searchArticleLists(String search, String articleType);

    /**
     * 搜索文章内容根据文章id，处理为纯文本
     * @param articleId
     * @return
     */
    public String searchArticleContent(Long articleId);
}
