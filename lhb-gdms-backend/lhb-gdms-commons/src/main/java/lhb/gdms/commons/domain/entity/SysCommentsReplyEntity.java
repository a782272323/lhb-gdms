package lhb.gdms.commons.domain.entity;

import lhb.gdms.commons.base.persistence.BaseDateEntity;

/**
 * @Description  表 sys_comments_reply 评论回复关系表 实体类
 * @author Herbie Leung(梁鸿斌)
 * @date 2021/1/16
 * @time 11:50
 */
public class SysCommentsReplyEntity extends BaseDateEntity {
    private static final long serialVersionUID = -90000118L;
    /**
     * id
     */
    private Long sysCommentsReplyId;
    /**
     * 评论表id
     */
    private Long sysArticleCommentsId;
    /**
     * 回复类型
     * 可以是针对评论的回复(comment)，
     * 也可以是针对回复的回复(reply)，
     * 通过这个字段来区分两种情况
     */
    private String commentsReplyType;
    /**
     * 回复内容
     */
    private String commentsReplyContent;
    /**
     * 回复用户id，即该条回复评论的评论的用户id
     */
    private Long fromSysUserId;
    /**
     * 目标用户id，
     * 即发布上面文章评论表id字段的用户id。
     * 当comments_reply_type是reply时，
     * 这里输入的是要回复这条回复的评论的用户id，
     * 当comments_reply_type是comment时，为空。
     */
    private Long toSysUserId;

    @Override
    public String toString() {
        return "SysCommentsReplyEntity{" +
                "sysCommentsReplyId=" + sysCommentsReplyId +
                ", sysArticleCommentsId=" + sysArticleCommentsId +
                ", commentsReplyType='" + commentsReplyType + '\'' +
                ", commentsReplyContent='" + commentsReplyContent + '\'' +
                ", fromSysUserId=" + fromSysUserId +
                ", toSysUserId=" + toSysUserId +
                '}';
    }

    public Long getSysCommentsReplyId() {
        return sysCommentsReplyId;
    }

    public void setSysCommentsReplyId(Long sysCommentsReplyId) {
        this.sysCommentsReplyId = sysCommentsReplyId;
    }

    public Long getSysArticleCommentsId() {
        return sysArticleCommentsId;
    }

    public void setSysArticleCommentsId(Long sysArticleCommentsId) {
        this.sysArticleCommentsId = sysArticleCommentsId;
    }

    public String getCommentsReplyType() {
        return commentsReplyType;
    }

    public void setCommentsReplyType(String commentsReplyType) {
        this.commentsReplyType = commentsReplyType;
    }

    public String getCommentsReplyContent() {
        return commentsReplyContent;
    }

    public void setCommentsReplyContent(String commentsReplyContent) {
        this.commentsReplyContent = commentsReplyContent;
    }

    public Long getFromSysUserId() {
        return fromSysUserId;
    }

    public void setFromSysUserId(Long fromSysUserId) {
        this.fromSysUserId = fromSysUserId;
    }

    public Long getToSysUserId() {
        return toSysUserId;
    }

    public void setToSysUserId(Long toSysUserId) {
        this.toSysUserId = toSysUserId;
    }
}
