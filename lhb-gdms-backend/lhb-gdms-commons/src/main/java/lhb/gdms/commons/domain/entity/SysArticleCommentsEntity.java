package lhb.gdms.commons.domain.entity;

import lhb.gdms.commons.base.persistence.BaseDateEntity;

/**
 * @Description  表 sys_article_comments 文章评论关系表
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/31
 * @time 02:13
 */
public class SysArticleCommentsEntity extends BaseDateEntity {
    private static final long serialVersionUID = -90000055L;
    /**
     * id
     */
    private Long sysArticleCommentsId;
    /**
     * 评论内容
     */
    private String articleCommentsContent;
    /**
     * 文章id
     */
    private Long articleId;
    /**
     * 发表此评论的用户id
     */
    private Long sysUserId;

    @Override
    public String toString() {
        return "SysArticleCommentsEntity{" +
                "sysArticleCommentsId=" + sysArticleCommentsId +
                ", articleCommentsContent='" + articleCommentsContent + '\'' +
                ", articleId=" + articleId +
                ", sysUserId=" + sysUserId +
                '}';
    }

    public Long getSysArticleCommentsId() {
        return sysArticleCommentsId;
    }

    public void setSysArticleCommentsId(Long sysArticleCommentsId) {
        this.sysArticleCommentsId = sysArticleCommentsId;
    }

    public String getArticleCommentsContent() {
        return articleCommentsContent;
    }

    public void setArticleCommentsContent(String articleCommentsContent) {
        this.articleCommentsContent = articleCommentsContent;
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
}
