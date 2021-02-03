package lhb.gdms.consumer.blog.service;

import lhb.gdms.commons.base.persistence.BaseService;
import lhb.gdms.commons.domain.entity.SysArticleCommentsEntity;

/**
 * @Description  
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/31 
 * @time 02:59
 */
public interface SysArticleCommentsService extends BaseService<SysArticleCommentsEntity> {

    /**
     * 判断是否是文章作者发表的评论
     * @param articleId
     * @param sysUserId
     * @return
     */
    Boolean getCommentsListsBySysUserIdAndArticleId(Long articleId, Long sysUserId);

    /**
     * 根据用户id查询用户发表的评论总数
     * @param sysUserId
     * @return
     */
    Integer getHomeUserCommentsCountAll(Long sysUserId);
}
