package lhb.gdms.consumer.blog.service;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/18
 * @time 14:05
 */
public interface OtherService {

    /**
     * 查询该标签下有多少人关注
     * @param labelId
     * @return
     */
    public Integer getLabelFocusCount(Long labelId);

    /**
     * 判断当前登录用户是否关注了该标签
     * @param sysUserId
     * @param labelId
     * @return
     */
    public Boolean findLabelFocusInfoById(Long sysUserId, Long labelId);

    /**
     * 取消标签关注
     * @param labelId
     * @param sysUserId
     * @return
     */
    public Integer deleteLabelFocusOne(Long labelId, Long sysUserId);

    /**
     * 校验当前评论的点赞是否是当前用户
     * @param sysArticleCommentsId
     * @param sysUserId
     * @return
     */
    Boolean checkCommentsPraiseByTwoId(Long sysArticleCommentsId, Long sysUserId);

    /**
     * 校验当前回复的点赞是否是当前用户
     * @param sysCommentsReplyId
     * @param sysUserId
     * @return
     */
    Boolean checkReplyPraiseByTwoId(Long sysCommentsReplyId, Long sysUserId);

    /**
     * 根据文章id统计评论和回复总数
     * @param articleId
     * @return
     */
    Integer getArticleCommentsCountAll(Long articleId);

    /**
     * 回复点赞
     * @param sysCommentsReplyId
     * @param sysUserId
     * @return
     */
    Integer insertReplyPraise(Long sysCommentsReplyId, Long sysUserId);

    /**
     * 取消回复的点赞
     * @param sysCommentsReplyId
     * @param sysUserId
     * @return
     */
    Integer deleteReplyPraise(Long sysCommentsReplyId, Long sysUserId);

    /**
     * 用户主页点赞列表
     * @param userId
     * @return
     */
    List<Map<String, Object>> getHomePraiseLists(Long userId);

    /**
     * 消息模块评论相关列表详情
     * @param sysUserId
     * @return
     */
    public List<Map<String, Object>> getMessageCommentsLists(Long sysUserId);

    /**
     * 消息模块点赞相关列表详情
     * @param sysUserId
     * @return
     */
    public List<Map<String, Object>> getMessagePraiseLists(Long sysUserId);
}
