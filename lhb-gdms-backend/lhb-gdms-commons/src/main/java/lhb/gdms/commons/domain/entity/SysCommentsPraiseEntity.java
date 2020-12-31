package lhb.gdms.commons.domain.entity;

import lhb.gdms.commons.base.persistence.BaseDateEntity;

/**
 * @Description  表 sys_comments_praise 评论点赞表实体类
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/31
 * @time 01:48
 */
public class SysCommentsPraiseEntity extends BaseDateEntity {
    private static final long serialVersionUID = 4132915166555697952L;
    /**
     * id
     */
    private Long sysCommentsPraiseId;
    /**
     * 评论id
     */
    private Long sysArticleCommentsId;
    /**
     * 点赞的用户id
     */
    private Long sysUserId;
    /**
     * 点赞状态
     */
    private Integer sysCommentsPraiseStatus;

    @Override
    public String toString() {
        return "SysCommentsPraiseEntity{" +
                "sysCommentsPraiseId=" + sysCommentsPraiseId +
                ", sysArticleCommentsId=" + sysArticleCommentsId +
                ", sysUserId=" + sysUserId +
                ", sysCommentsPraiseStatus=" + sysCommentsPraiseStatus +
                '}';
    }

    public Long getSysCommentsPraiseId() {
        return sysCommentsPraiseId;
    }

    public void setSysCommentsPraiseId(Long sysCommentsPraiseId) {
        this.sysCommentsPraiseId = sysCommentsPraiseId;
    }

    public Long getSysArticleCommentsId() {
        return sysArticleCommentsId;
    }

    public void setSysArticleCommentsId(Long sysArticleCommentsId) {
        this.sysArticleCommentsId = sysArticleCommentsId;
    }

    public Long getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Long sysUserId) {
        this.sysUserId = sysUserId;
    }

    public Integer getSysCommentsPraiseStatus() {
        return sysCommentsPraiseStatus;
    }

    public void setSysCommentsPraiseStatus(Integer sysCommentsPraiseStatus) {
        this.sysCommentsPraiseStatus = sysCommentsPraiseStatus;
    }
}
