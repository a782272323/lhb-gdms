package lhb.gdms.consumer.blog.mapper;

import lhb.gdms.commons.base.persistence.BaseMapper;
import lhb.gdms.commons.domain.entity.SysArticleCommentsEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
}
