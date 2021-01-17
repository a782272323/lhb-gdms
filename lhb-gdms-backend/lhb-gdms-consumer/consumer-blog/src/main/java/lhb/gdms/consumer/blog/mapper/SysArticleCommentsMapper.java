package lhb.gdms.consumer.blog.mapper;

import lhb.gdms.commons.base.persistence.BaseMapper;
import lhb.gdms.commons.domain.entity.SysArticleCommentsEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/31
 * @time 02:15
 */
@Repository
public interface SysArticleCommentsMapper extends BaseMapper<SysArticleCommentsEntity> {

    /**
     * 查询评论数量
     * @param articleId
     * @return
     */
    Integer getArticleCommentsCount(@Param("articleId") Long articleId);

    /**
     * 根据文章id获取文章评论表全部列表信息
     * @param articleId
     * @return
     */
    List<Map<String, Object>> getCommentsLists(@Param("articleId") Long articleId);

    /**
     * 根据文章id和用户id查询文章评论表全部列表信息
     * @param articleId
     * @param sysUserId
     * @return
     */
    List<SysArticleCommentsEntity> getCommentsListsBySysUserIdAndArticleId(@Param("articleId") Long articleId,
                                                                           @Param("sysUserId") Long sysUserId);

    /**
     * 根据文章id获取评论表详情
     * @param articleId
     * @return
     */
    List<SysArticleCommentsEntity> getCommentsInfoByArticleId(@Param("articleId") Long articleId);
}
