package lhb.gdms.consumer.blog.service;

import lhb.gdms.commons.base.persistence.BaseService;
import lhb.gdms.commons.domain.entity.SysCommentsReplyEntity;

/**
 * @Description  
 * @author Herbie Leung(梁鸿斌)
 * @date 2021/1/16 
 * @time 08:41
 */
public interface SysCommentsReplyService extends BaseService<SysCommentsReplyEntity> {

    /**
     * 根据用户id查询用户发表的回复总数
     * @param sysUserId
     * @return
     */
    Integer getHomeUserReplyCountAll(Long sysUserId);
}
