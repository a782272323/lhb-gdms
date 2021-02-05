package lhb.gdms.consumer.admin.controller;

import com.google.common.collect.Maps;
import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.constant.ResponseConstant;
import lhb.gdms.commons.domain.vo.QueryVO;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.configuration.aop.config.PrintlnLog;
import lhb.gdms.configuration.utils.SecurityOauth2Utils;
import lhb.gdms.consumer.admin.domain.vo.BlogOverviewSearchVO;
import lhb.gdms.consumer.admin.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Description  博客总览 controller
 * @author Herbie Leung(梁鸿斌)
 * @date 2021/2/4
 * @time 09:17
 */
@RestController
@CrossOrigin
public class BlogOverviewController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SecurityOauth2Utils securityOauth2Utils;

    @Autowired
    private ArticleService articleService;

    /**
     * 获取文章列表
     * @param queryVO
     * @return
     */
    @PrintlnLog(description = "获取文章列表-controller")
    @GetMapping("/admin/blog/overview/article/lists")
    public BaseResult getArticleLists(QueryVO queryVO) {
        Map<String, Object> map = Maps.newHashMap();
        List<Map<String, Object>> list = articleService.getArticleLists(queryVO);
        map.put("articleLists", list);
        map.put("count", articleService.getArticleListsCount(queryVO));
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

    /**
     * 搜索文章
     * @param queryVO
     * @param searchVO
     * @return
     */
    @PrintlnLog(description = "搜索文章-controller")
    @GetMapping("/admin/blog/overview/article/lists/search")
    public BaseResult searchArticleLists(QueryVO queryVO, BlogOverviewSearchVO searchVO) {
        Map<String, Object> map = Maps.newHashMap();
        List<Map<String, Object>> list = articleService.searchArticleLists(queryVO, searchVO);
        map.put("articleLists", list);
        map.put("count", articleService.searchArticleListsCount(queryVO, searchVO));
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

    /**
     * 根据文章id查询文章详情
     * @param articleId
     * @return
     */
    @PrintlnLog(description = "根据文章id查询文章详情-controller")
    @GetMapping("/admin/blog/overview/article/list/{articleId}")
    public BaseResult getArticleList(@PathVariable("articleId") Long articleId) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("articleList", articleService.getArticleList(articleId));
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

}
