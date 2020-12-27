package lhb.gdms.commons.domain.entity;

import lhb.gdms.commons.base.persistence.BaseDateEntity;

/**
 * @Description  文章表 sys_article 实体类
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/22
 * @time 01:32
 */
public class SysArticleEntity extends BaseDateEntity {

    private static final long serialVersionUID = -90000105L;
    /**
     * id
     */
    private Long articleId;
    /**
     * 文章标题
     */
    private String articleTitle;
    /**
     * 文章内容
     */
    private String articleContent;
    /**
     * 文章类型
     * tinyMCE 富文本编译器
     * Markdown
     */
    private String articleType;
    /**
     * 文章被阅览数量
     */
    private Integer articleBrowseSum;
    /**
     * 文章封面大图url
     */
    private String articleImgUrl;
    /**
     * 七牛云key
     */
    private String articleImgKey;
    /**
     * 用户id
     */
    private Long sysUserId;
    /**
     * 标签id
     */
    private Long labelId;

    @Override
    public String toString() {
        return "SysArticleEntity{" +
                "articleId=" + articleId +
                ", articleTitle='" + articleTitle + '\'' +
                ", articleContent='" + articleContent + '\'' +
                ", articleType='" + articleType + '\'' +
                ", articleBrowseSum=" + articleBrowseSum +
                ", articleImgUrl='" + articleImgUrl + '\'' +
                ", articleImgKey='" + articleImgKey + '\'' +
                ", sysUserId=" + sysUserId +
                ", labelId=" + labelId +
                '}';
    }

    public Integer getArticleBrowseSum() {
        return articleBrowseSum;
    }

    public void setArticleBrowseSum(Integer articleBrowseSum) {
        this.articleBrowseSum = articleBrowseSum;
    }

    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public String getArticleImgUrl() {
        return articleImgUrl;
    }

    public void setArticleImgUrl(String articleImgUrl) {
        this.articleImgUrl = articleImgUrl;
    }

    public String getArticleImgKey() {
        return articleImgKey;
    }

    public void setArticleImgKey(String articleImgKey) {
        this.articleImgKey = articleImgKey;
    }

    public Long getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Long sysUserId) {
        this.sysUserId = sysUserId;
    }
}
