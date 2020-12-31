package lhb.gdms.commons.domain.entity;

import lhb.gdms.commons.base.persistence.BaseDateEntity;

import java.util.Iterator;

/**
 * @Description  表 sys_article_praise 实体类
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/31
 * @time 01:58
 */
public class SysArticlePraiseEntity extends BaseDateEntity {
    private static final long serialVersionUID = -90000041L;
    /**
     * id
     */
    private Long sysArticlePraiseId;
    /**
     * 文章id
     */
    private Long articleId;
    /**
     * 点赞的用户id
     */
    private Long sysUserId;
    /**
     * 点赞状态
     */
    private Integer sysArticlePraiseStatus;

    @Override
    public String toString() {
        return "SysArticlePraiseEntity{" +
                "sysArticlePraiseId=" + sysArticlePraiseId +
                ", articleId=" + articleId +
                ", sysUserId=" + sysUserId +
                ", sysArticlePraiseIdStatus=" + sysArticlePraiseStatus +
                '}';
    }

    public Long getSysArticlePraiseId() {
        return sysArticlePraiseId;
    }

    public void setSysArticlePraiseId(Long sysArticlePraiseId) {
        this.sysArticlePraiseId = sysArticlePraiseId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Long sysUserId) {
        this.sysUserId = sysUserId;
    }

    public Integer getSysArticlePraiseStatus() {
        return sysArticlePraiseStatus;
    }

    public void setSysArticlePraiseStatus(Integer sysArticlePraiseStatus) {
        this.sysArticlePraiseStatus = sysArticlePraiseStatus;
    }
}
