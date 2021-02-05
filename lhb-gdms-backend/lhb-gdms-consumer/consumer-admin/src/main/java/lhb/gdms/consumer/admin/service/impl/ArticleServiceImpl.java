package lhb.gdms.consumer.admin.service.impl;

import com.github.pagehelper.PageHelper;
import lhb.gdms.commons.domain.vo.QueryVO;
import lhb.gdms.consumer.admin.domain.vo.BlogOverviewSearchVO;
import lhb.gdms.consumer.admin.mapper.ArticleMapper;
import lhb.gdms.consumer.admin.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2021/2/3
 * @time 19:55
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 获取文章发布总数
     * @param sysUserId
     * @return
     */
    @Override
    public Integer getArticleCount(Long sysUserId) {
        return articleMapper.getArticleCount(sysUserId);
    }

    /**
     * 获取文章列表
     * @param queryVO
     * @return
     */
    @Override
    public List<Map<String, Object>> getArticleLists(QueryVO queryVO) {
        PageHelper.startPage(queryVO.getOffSet(), queryVO.getLimit());
        return articleMapper.getArticleLists(queryVO);
    }

    /**
     * 获取文章列表总数据量
     * @param queryVO
     * @return
     */
    @Override
    public Integer getArticleListsCount(QueryVO queryVO) {
        List<Map<String, Object>> list = articleMapper.getArticleLists(queryVO);
        return list != null && list.size() > 0 ? list.size() : 0;
    }

    /**
     * 搜索文章
     * @param queryVO
     * @param searchVO
     * @return
     */
    @Override
    public List<Map<String, Object>> searchArticleLists(QueryVO queryVO, BlogOverviewSearchVO searchVO) {
        PageHelper.startPage(queryVO.getOffSet(), queryVO.getLimit());
        return articleMapper.searchArticleLists(queryVO, searchVO);
    }

    /**
     * 搜索文章总数据量
     * @param queryVO
     * @param searchVO
     * @return
     */
    @Override
    public Integer searchArticleListsCount(QueryVO queryVO, BlogOverviewSearchVO searchVO) {
        List<Map<String, Object>> list = articleMapper.searchArticleLists(queryVO, searchVO);
        return list.size() > 0 && list != null ? list.size() : 0;
    }

    /**
     * 根据文章id查询文章详情
     * @param articleId
     * @return
     */
    @Override
    public List<Map<String, Object>> getArticleList(Long articleId) {
        return articleMapper.getArticleList(articleId);
    }

    /**
     * 按时间段查询新增文章数
     * @param timeType
     * @return
     */
    @Override
    public Integer getArticleCountByTime(String timeType) {
        return articleMapper.getArticleCountByTime(timeType);
    }
}
