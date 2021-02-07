package lhb.gdms.consumer.blog.service.impl;

import lhb.gdms.commons.base.persistence.BaseServiceImpl;
import lhb.gdms.commons.domain.entity.SysArticleEntity;
import lhb.gdms.commons.utils.HtmlToTextUtils;
import lhb.gdms.consumer.blog.mapper.SysArticleMapper;
import lhb.gdms.consumer.blog.mapper.SysArticlePraiseMapper;
import lhb.gdms.consumer.blog.service.OtherService;
import lhb.gdms.consumer.blog.service.SysArticlePraiseService;
import lhb.gdms.consumer.blog.service.SysArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/25
 * @time 17:17
 */
@Service
public class SysArticleServiceImpl extends BaseServiceImpl<SysArticleEntity, SysArticleMapper> implements SysArticleService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysArticleMapper sysArticleMapper;

    @Autowired
    private SysArticlePraiseMapper sysArticlePraiseMapper;

    @Autowired
    private SysArticlePraiseService sysArticlePraiseService;

    @Autowired
    private OtherService otherService;

    /**
     * 新增
     * @param entity
     * @return 返回主键id
     */
    @Override
    public Long insertData(SysArticleEntity entity) {
        return sysArticleMapper.insertData(entity) > 0
                ? entity.getArticleId()
                : null;
    }

    /**
     * 查询该标签下有多少文章
     * @param labelId
     * @return
     */
    @Override
    public Integer getLabelArticleCount(Long labelId) {
        Integer count = sysArticleMapper.getLabelArticleCount(labelId);
        return count == null ? 0 : count;
    }

    /**
     * 查询用户所有获得的点赞总数
     * @param sysUserId
     * @return
     */
    @Override
    public Integer getArticlePriseAll(Long sysUserId) {
        List<Map<String, Object>> list = sysArticleMapper.getArticleBySysUserId(sysUserId);
        AtomicReference<Integer> sum = new AtomicReference<>(0);
        list.stream().forEach(item -> {
            Long articleId = Long.parseLong(item.get("articleId").toString());
            int articlePraise = sysArticlePraiseMapper.getArticlePraiseCountBySysUserId(articleId, sysUserId);
            sum.updateAndGet(v -> v + articlePraise);
        });
        return sum.get();
    }

    /**
     * 查询用户所有被阅读数
     * @param sysUserId
     * @return
     */
    @Override
    public Integer getArticleBrowseAll(Long sysUserId) {
        List<Map<String, Object>> list = sysArticleMapper.getArticleBySysUserId(sysUserId);
        AtomicReference<Integer> sum = new AtomicReference<>(0);
        list.stream().forEach(item -> {
            int articleBrowseSum = Integer.parseInt(item.get("articleBrowseSum").toString());
            sum.updateAndGet(v -> v + articleBrowseSum);
        });
        return sum.get();
    }

    /**
     * 按照最新首页文章发布时间降序排序-推荐
     * @param userId 当前登录用户id
     * @return
     */
    @Override
    public List<Map<String, Object>> getHomeArticleLists(Long userId) {
        List<Map<String, Object>> list = sysArticleMapper.getHomeArticleLists();
        if (list != null && list.size() > 0) {
            return getHomeArticleOthers(list, userId);
        }
        return list;
    }

    /**
     * 按照最热门首页文章来排序-推荐
     * @param userId
     * @return
     */
    @Override
    public List<Map<String, Object>> getHomeArticlesListsByHostest(Long userId) {
        List<Map<String, Object>> list = sysArticleMapper.getHomeArticleLists();
        if (list != null && list.size() > 0) {
            list = getHomeArticleOthers(list, userId);
            // 按照最热门进行排序: 1.被阅读数 2.点赞数 3.评论数
            return getHomeArticleHostest(list);
        }
        return list;
    }

    /**
     * 按照最新首页文章发布时间降序排序-动态标签
     * @param labelId
     * @param userId
     * @return
     */
    @Override
    public List<Map<String, Object>> getHomeArticlesListsByLabelId(Long labelId, Long userId) {
        List<Map<String, Object>> list = sysArticleMapper.getHomeArticleListsByLabelId(labelId);
        if (list.size() > 0 && list != null) {
            return getHomeArticleOthers(list, userId);
        }
        return list;
    }

    /**
     * 按照最热门首页文章来排序-动态标签
     * @param labelId
     * @param userId
     * @return
     */
    @Override
    public List<Map<String, Object>> getHomeArticlesListsByLabelIdByHostest(Long labelId, Long userId) {
        List<Map<String, Object>> list = sysArticleMapper.getHomeArticleListsByLabelId(labelId);
        if (list.size() > 0 && list != null) {
            list = getHomeArticleOthers(list, userId);
            return getHomeArticleHostest(list);
        }
        return list;
    }

    /**
     * 根据用户id查询文章总数
     * @param sysUserId
     * @return
     */
    @Override
    public Integer getHomeUserArticleCount(Long sysUserId) {
        return sysArticleMapper.getHomeUserArticleCount(sysUserId);
    }

    /**
     *
     * @param sysUserId
     * @return
     */
    @Override
    public List<Map<String, Object>> getArticleBySysUserId(Long sysUserId) {
        return sysArticleMapper.getArticleBySysUserId(sysUserId);
    }

    /**
     * 用户主页评论列表
     * @param userId
     * @return
     */
    @Override
    public List<Map<String, Object>> getHomeCommentsLists(Long userId) {
        return sysArticleMapper.getHomeCommentsLists(userId);
    }

    /**
     * 搜索文章
     * @param search
     * @param articleType
     * @return
     */
    @Override
    public List<Map<String, Object>> searchArticleLists(String search, String articleType) {
        return sysArticleMapper.searchArticleLists(search, articleType);
    }

    /**
     * 搜索文章内容根据文章id，处理为纯文本
     * @param articleId
     * @return
     */
    @Override
    public String searchArticleContent(Long articleId) {
        String articleContent = sysArticleMapper.searchArticleContent(articleId);
        return HtmlToTextUtils.getContent(articleContent);
    }

    /**
     * 登录用户关注的人发布的文章
     * @param sysUserId
     * @return
     */
    @Override
    public List<Map<String, Object>> getUserFocusArticleLists(Long sysUserId) {
        List<Map<String, Object>> list = sysArticleMapper.getUserFocusArticleLists(sysUserId);
        Long i = Long.valueOf(-1);

        list.stream().forEach(item -> {
            Long articleId = Long.parseLong(item.get("articleId").toString());
            // 获取文章评论数量
            item.put("articleCommentsCount", otherService.getArticleCommentsCountAll(articleId));
            // 判断是否是当前用户点赞
            if (sysUserId.longValue() != i.longValue()) {
                item.put("isPraise", sysArticlePraiseService.findInfoById(articleId, sysUserId));
            } else {
                item.put("isPraise", false);
            }
        });
        return list;
    }

    /**
     * 对主页文章列表进行其他处理
     * @param list
     * @param userId 当前登录用户id
     * @return
     */
    private List<Map<String, Object>> getHomeArticleOthers(List<Map<String, Object>> list, Long userId) {
        list.stream().forEach(item -> {
            Long articleId = Long.parseLong(item.get("articleId").toString());
            Long sysUserId = Long.parseLong(item.get("sysUserId").toString());
            Long i = Long.valueOf(-1);
            // 获取每篇文章点赞数量
            item.put("articlePraiseCount", sysArticlePraiseService.getArticlePraiseCount(articleId));
            // 获取文章评论数量
            item.put("articleCommentsCount", otherService.getArticleCommentsCountAll(articleId));
            // 判断是否是当前用户点赞
            if (userId.longValue() != i.longValue()) {
                item.put("isPraise", sysArticlePraiseService.findInfoById(articleId, userId));
            } else {
                item.put("isPraise", false);
            }
        });
        return list;
    }

    /**
     * 对首页文章列表进行最热门排序处理
     * @param list
     * @return
     */
    private List<Map<String, Object>> getHomeArticleHostest(List<Map<String, Object>> list) {
        // 按照最热门进行排序: 1.被阅读数 2.点赞数 3.评论数
        Collections.sort(list, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> m1, Map<String, Object> m2) {
                // 先对被阅读数降序排序
                Integer articleBrowseSum1 = Integer.valueOf(m1.get("articleBrowseSum").toString());
                Integer articleBrowseSum2 = Integer.valueOf(m2.get("articleBrowseSum").toString());
                int sort1 = articleBrowseSum2.compareTo(articleBrowseSum1);
                if (sort1 != 0) {
                    return sort1;
                }
                // 紧接着对点赞数降序排序
                Integer articlePraiseCount1 = Integer.valueOf(m1.get("articleBrowseSum").toString());
                Integer articlePraiseCount2 = Integer.valueOf(m2.get("articleBrowseSum").toString());
                int sort2 = articlePraiseCount2.compareTo(articlePraiseCount1);
                if (sort2 != 0) {
                    return sort2;
                }
                // 最后对评论数进行降序排序
                Integer articleCommentsCount1 = Integer.valueOf(m1.get("articleCommentsCount").toString());
                Integer articleCommentsCount2 = Integer.valueOf(m2.get("articleCommentsCount").toString());
                return articleCommentsCount2.compareTo(articleCommentsCount1);
            }
        });
        return list;
    }
}
