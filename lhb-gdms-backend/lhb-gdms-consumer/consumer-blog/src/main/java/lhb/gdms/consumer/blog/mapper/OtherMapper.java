package lhb.gdms.consumer.blog.mapper;

import lhb.gdms.commons.domain.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description  调用各种表的 mapper
 * 由于有些表的操作只需要一次，没必要新建个mapper去对接
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/18
 * @time 14:04
 */
@Repository
public interface OtherMapper {

    /**
     * 获取全部系统头像信息
     * @return
     */
    List<SysUserIconEntity> getIconLists();

    /**
     * 根据id查询系统头像信息
     * @param sysUserIconId
     * @return
     */
    SysUserIconEntity getIconInfoById(@Param("sysUserIconId") Long sysUserIconId);

    /**
     * 获取全部标签信息
     * @return
     */
    List<SysLabelEntity> getLabelLists();

    /**
     * 搜索标签信息
     * @param keyWord
     * @return
     */
    List<SysLabelEntity> queryLabelLists(@Param("keyWord") String keyWord);

    /**
     * 查询该标签下有多少人关注
     * @param labelId
     * @return
     */
    Integer getLabelFocusCount(@Param("labelId") Long labelId);

    /**
     * 判断当前登录用户是否关注了该标签
     * @param sysUserId
     * @param labelId
     * @return
     */
    SysLabelFocusEntity findLabelFocusInfoById(@Param("sysUserId") Long sysUserId,
                                               @Param("labelId") Long labelId);

    /**
     * 取消标签关注
     * @param sysUserId
     * @param labelId
     * @return
     */
    Integer deleteLabelFocusOne(@Param("sysUserId") Long sysUserId,
                                @Param("labelId") Long labelId);

    /**
     * 添加标签关注
     * @param entity
     * @return
     */
    Integer insertLabelFocus(@Param("entity") SysLabelFocusEntity entity);

    /**
     * 根据文章和收藏集id查询 sys_article_collection 数据
     * @param articleId
     * @param sysCollectionId
     * @return
     */
    List<Map<String, Object>> getArticleCollectionInfoByTwoId(Long articleId, Long sysCollectionId);

    /**
     * 添加文章到收藏集
     * @param articleId
     * @param sysCollectionId
     * @return
     */
    Integer insertArticleToCollection(@Param("articleId") Long articleId,
                                      @Param("sysCollectionId") Long sysCollectionId);

    /**
     * 取消文章的收藏
     * @param sysCollectionId
     * @return
     */
    Integer deleteArticleToCollection(@Param("articleId") Long articleId,
                                      @Param("sysCollectionId") Long sysCollectionId);

    /**
     * 获取评论点赞数量
     * @param sysArticleCommentsId
     * @return
     */
    Integer getCommentsPraiseCount(@Param("sysArticleCommentsId") Long sysArticleCommentsId);

    /**
     * 根据评论表id和用户id查询数据
     * @param sysArticleCommentsId
     * @param sysUserId
     * @return
     */
    List<Map<String, Object>> getCommentsPraiseByTwoId(@Param("sysArticleCommentsId") Long sysArticleCommentsId,
                                                           @Param("sysUserId") Long sysUserId);

    /**
     * 点赞评论
     * @param entity
     * @return
     */
    Integer insertCommentsPraise(@Param("entity") SysCommentsPraiseEntity entity);

    /**
     * 取消评论点赞
     * @param sysArticleCommentsId
     * @param sysUserId
     * @return
     */
    Integer deleteCommentsPraise(@Param("sysArticleCommentsId") Long sysArticleCommentsId,
                                 @Param("sysUserId") Long sysUserId);

    /**
     * 获取回复点赞数量
     * @param sysCommentsReplyId
     * @return
     */
    Integer getReplyPraiseCount(@Param("sysCommentsReplyId") Long sysCommentsReplyId);

    /**
     * 根据回复id和用户id查询回复点赞表数据
     * @param sysCommentsReplyId
     * @param sysUserId
     * @return
     */
    List<Map<String, Object>> getReplyPraiseInfoByTwoId(@Param("sysCommentsReplyId") Long sysCommentsReplyId,
                                                         @Param("sysUserId") Long sysUserId);

    /**
     * 回复点赞
     * @param entity
     * @return
     */
    Integer insertReplyPraise(@Param("entity") SysReplyPraiseEntity entity);

    /**
     * 取消回复的点赞
     * @param sysCommentsReplyId
     * @param sysUserId
     * @return
     */
    Integer deleteReplyPraise(@Param("sysCommentsReplyId") Long sysCommentsReplyId,
                              @Param("sysUserId") Long sysUserId);
}
