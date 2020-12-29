package lhb.gdms.commons.domain.entity;

import java.io.Serializable;

/**
 * @Description  文章收藏关系表 sys_article_collection 实体类
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/27
 * @time 18:10
 */
public class SysArticleCollectionEntity implements Serializable {
    private static final long serialVersionUID = -90000045L;
    /**
     * id
     */
    private Long sysArticleCollectionId;
    /**
     * 文章id
     */
    private Long articleId;
    /**
     * 收藏表id
     */
    private Long sysCollectionId;

    @Override
    public String toString() {
        return "SysArticleCollectionEntity{" +
                "sysArticleCollectionId=" + sysArticleCollectionId +
                ", articleId=" + articleId +
                ", sysCollectionId=" + sysCollectionId +
                '}';
    }

    public Long getSysArticleCollectionId() {
        return sysArticleCollectionId;
    }

    public void setSysArticleCollectionId(Long sysArticleCollectionId) {
        this.sysArticleCollectionId = sysArticleCollectionId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getSysCollectionId() {
        return sysCollectionId;
    }

    public void setSysCollectionId(Long sysCollectionId) {
        this.sysCollectionId = sysCollectionId;
    }
}
