package lhb.gdms.consumer.blog.mapper;

import lhb.gdms.commons.base.persistence.BaseMapper;
import lhb.gdms.commons.domain.entity.SysArticlePraiseEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/31
 * @time 02:01
 */
@Repository
public interface SysArticlePraiseMapper extends BaseMapper<SysArticlePraiseEntity> {

    /**
     * 查询文章点赞总数
     * @param articleId
     * @return
     */
    Integer getArticlePraiseCount(@Param("articleId") Long articleId);

    /**
     * 根据用户id和文章id查询点赞总数
     * @param articleId
     * @param sysUserId
     * @return
     */
    Integer getArticlePraiseCountBySysUserId(@Param("articleId") Long articleId,
                                             @Param("sysUserId") Long sysUserId);

    /**
     * 根据文章id和用户id查询是否存在数据
     * @param articleId
     * @param sysUserId
     * @return
     */
    SysArticlePraiseEntity findInfoById(@Param("articleId") Long articleId,
                                        @Param("sysUserId") Long sysUserId);

    /**
     * 根据用户id查询用户点赞总数
     * @param sysUserId
     * @return
     */
    Integer getUserArticlePraise(@Param("sysUserId") Long sysUserId);
}
