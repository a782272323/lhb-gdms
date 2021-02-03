package lhb.gdms.consumer.blog.service.impl;

import lhb.gdms.commons.base.persistence.BaseServiceImpl;
import lhb.gdms.commons.domain.entity.SysArticlePraiseEntity;
import lhb.gdms.consumer.blog.mapper.SysArticlePraiseMapper;
import lhb.gdms.consumer.blog.service.SysArticlePraiseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/31
 * @time 02:03
 */
@Service
public class SysArticlePraiseServiceImpl extends BaseServiceImpl<SysArticlePraiseEntity, SysArticlePraiseMapper> implements SysArticlePraiseService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysArticlePraiseMapper sysArticlePraiseMapper;

    /**
     * 查询文章点赞总数
     * @param articleId
     * @return
     */
    @Override
    public Integer getArticlePraiseCount(Long articleId) {
        Integer count = sysArticlePraiseMapper.getArticlePraiseCount(articleId);
        logger.debug("查询文章点赞总数 = " + count);
        if (count == null || count <= 0) {
            return 0;
        }
        return count;
    }

    /**
     * 根据文章id和用户id查询是否存在数据
     * @param articleId
     * @param sysUserId
     * @return
     */
    @Override
    public boolean findInfoById(Long articleId, Long sysUserId) {
        if (sysArticlePraiseMapper.findInfoById(articleId, sysUserId) != null) {
            return true;
        }
        return false;
    }

    /**
     * 根据用户id和文章id查询点赞总数
     * @param articleId
     * @param sysUserId
     * @return
     */
    @Override
    public Integer getArticlePraiseCountBySysUserId(Long articleId, Long sysUserId) {
        return sysArticlePraiseMapper.getArticlePraiseCountBySysUserId(articleId, sysUserId);
    }

    /**
     * 根据用户id查询用户点赞总数
     * @param sysUserId
     * @return
     */
    @Override
    public Integer getUserArticlePraise(Long sysUserId) {
        return sysArticlePraiseMapper.getUserArticlePraise(sysUserId);
    }
}
