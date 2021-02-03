package lhb.gdms.consumer.blog.mapper;

import lhb.gdms.commons.base.persistence.BaseMapper;
import lhb.gdms.commons.domain.entity.SysCommentsReplyEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2021/1/16
 * @time 11:55
 */
@Repository
public interface SysCommentsReplyMapper extends BaseMapper<SysCommentsReplyEntity> {

    /**
     * 根据评论id获取所有评论回复的列表信息
     * @param sysArticleCommentsId
     * @return
     */
    List<Map<String, Object>> getCommentsReplyLists(@Param("sysArticleCommentsId") Long sysArticleCommentsId);

    /**
     * 根据评论id统计回复数量
     * @param sysArticleCommentsId
     * @return
     */
    Integer getCommentsReplyCount(@Param("sysArticleCommentsId") Long sysArticleCommentsId);

    /**
     * 根据用户id查询用户发表的回复总数
     * @param sysUserId
     * @return
     */
    Integer getHomeUserReplyCountAll(@Param("sysUserId") Long sysUserId);
}
