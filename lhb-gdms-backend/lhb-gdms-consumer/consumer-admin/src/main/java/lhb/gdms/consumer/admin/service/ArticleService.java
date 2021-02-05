package lhb.gdms.consumer.admin.service;

import lhb.gdms.commons.domain.vo.QueryVO;
import lhb.gdms.consumer.admin.domain.vo.BlogOverviewSearchVO;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2021/2/3
 * @time 19:55
 */
public interface ArticleService {

    /**
     * 获取文章发布总数
     * @param sysUserId
     * @return
     */
    public Integer getArticleCount(Long sysUserId);

    /**
     * 获取文章列表
     * @param queryVO
     * @return
     */
    public List<Map<String, Object>> getArticleLists(QueryVO queryVO);

    /**
     * 获取文章列表总数据量
     * @param queryVO
     * @return
     */
    public Integer getArticleListsCount(QueryVO queryVO);

    /**
     * 搜索文章
     * @param queryVO
     * @param searchVO
     * @return
     */
    public List<Map<String, Object>> searchArticleLists(QueryVO queryVO, BlogOverviewSearchVO searchVO);

    /**
     * 搜索文章总数据量
     * @param queryVO
     * @param searchVO
     * @return
     */
    public Integer searchArticleListsCount(QueryVO queryVO, BlogOverviewSearchVO searchVO);

    /**
     * 根据文章id查询文章详情
     * @param articleId
     * @return
     */
    public List<Map<String, Object>> getArticleList(Long articleId);

    /**
     * 按时间段查询新增文章数
     * @param timeType
     * @return
     */
    public Integer getArticleCountByTime(String timeType);
}
