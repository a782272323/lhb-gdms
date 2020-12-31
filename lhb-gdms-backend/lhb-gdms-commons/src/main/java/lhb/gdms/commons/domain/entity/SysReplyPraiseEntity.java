package lhb.gdms.commons.domain.entity;

import lhb.gdms.commons.base.persistence.BaseDateEntity;

/**
 * @Description  表 sys_reply_praise 实体类
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/31
 * @time 01:50
 */
public class SysReplyPraiseEntity extends BaseDateEntity {
    private static final long serialVersionUID = -90000103L;
    /**
     * id
     */
    private Long sysReplyPraiseId;
    /**
     * 回复id
     */
    private Long sysCommentsReplyId;
    /**
     * 点赞的用户id
     */
    private Long sysUserId;
    /**
     * 点赞状态:
     * 0-取消赞
     * 1-已点赞
     */
    private Integer sysReplyPraiseStatus;

    @Override
    public String toString() {
        return "SysReplyPraiseEntity{" +
                "sysReplyPraiseId=" + sysReplyPraiseId +
                ", sysCommentsReplyId=" + sysCommentsReplyId +
                ", sysUserId=" + sysUserId +
                ", sysReplyPraiseStatus=" + sysReplyPraiseStatus +
                '}';
    }

    public Long getSysReplyPraiseId() {
        return sysReplyPraiseId;
    }

    public void setSysReplyPraiseId(Long sysReplyPraiseId) {
        this.sysReplyPraiseId = sysReplyPraiseId;
    }

    public Long getSysCommentsReplyId() {
        return sysCommentsReplyId;
    }

    public void setSysCommentsReplyId(Long sysCommentsReplyId) {
        this.sysCommentsReplyId = sysCommentsReplyId;
    }

    public Long getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Long sysUserId) {
        this.sysUserId = sysUserId;
    }

    public Integer getSysReplyPraiseStatus() {
        return sysReplyPraiseStatus;
    }

    public void setSysReplyPraiseStatus(Integer sysReplyPraiseStatus) {
        this.sysReplyPraiseStatus = sysReplyPraiseStatus;
    }
}
