package lhb.gdms.consumer.admin.mapper;

import lhb.gdms.commons.domain.vo.QueryVO;
import lhb.gdms.consumer.admin.domain.vo.BlogOverviewSearchVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2021/2/3
 * @time 19:00
 */
@Repository
public interface ArticleMapper {

    /**
     * 获取文章发布总数
     * @param sysUserId
     * @return
     */
    Integer getArticleCount(@Param("sysUserId") Long sysUserId);

    /**
     * 获取文章列表
     * @param queryVO
     * @return
     */
    List<Map<String, Object>> getArticleLists(@Param("queryVO") QueryVO queryVO);

    /**
     * 搜索文章
     * @param queryVO
     * @param searchVO
     * @return
     */
    List<Map<String, Object>> searchArticleLists(@Param("queryVO") QueryVO queryVO,
                                                 @Param("searchVO") BlogOverviewSearchVO searchVO);

    /**
     * 根据文章id查询文章详情
     * @param articleId
     * @return
     */
    List<Map<String, Object>> getArticleList(@Param("articleId") Long articleId);

    /**
     * 按时间段查询新增文章数
     * @param timeType
     * @return
     */
    Integer getArticleCountByTime(@Param("timeType") String timeType);

    /**
     * 根据年和月获取文章数量
     * @param year
     * @param month
     * @return
     */
    Integer getArticleListsByYearAndMonth(@Param("year") String year,
                                          @Param("month")String month);
}
