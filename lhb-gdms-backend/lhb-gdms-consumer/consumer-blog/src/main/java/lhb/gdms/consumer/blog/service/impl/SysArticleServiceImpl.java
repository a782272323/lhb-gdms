package lhb.gdms.consumer.blog.service.impl;

import lhb.gdms.commons.base.persistence.BaseServiceImpl;
import lhb.gdms.commons.domain.entity.SysArticleEntity;
import lhb.gdms.consumer.blog.mapper.SysArticleMapper;
import lhb.gdms.consumer.blog.mapper.SysArticlePraiseMapper;
import lhb.gdms.consumer.blog.service.SysArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * 查询用户所有点赞总数
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
            logger.debug("articlePraise = " + articlePraise);
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
}
