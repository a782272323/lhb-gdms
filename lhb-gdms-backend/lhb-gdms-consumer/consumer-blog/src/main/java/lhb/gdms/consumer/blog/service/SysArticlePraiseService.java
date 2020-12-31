package lhb.gdms.consumer.blog.service;

import lhb.gdms.commons.base.persistence.BaseService;
import lhb.gdms.commons.domain.entity.SysArticlePraiseEntity;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/31
 * @time 02:03
 */
public interface SysArticlePraiseService extends BaseService<SysArticlePraiseEntity> {

    /**
     * 查询文章点赞总数
     * @param articleId
     * @return
     */
    public Integer getArticlePraiseCount(Long articleId);

    /**
     * 根据文章id和用户id查询是否存在数据
     * @param articleId
     * @param sysUserId
     * @return
     */
    public boolean findInfoById(Long articleId, Long sysUserId);
}
