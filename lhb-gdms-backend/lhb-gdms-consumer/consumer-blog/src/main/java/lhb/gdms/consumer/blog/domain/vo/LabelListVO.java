package lhb.gdms.consumer.blog.domain.vo;

import lhb.gdms.commons.base.persistence.BaseDateEntity;

import java.io.Serializable;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/31
 * @time 20:29
 */
public class LabelListVO extends BaseDateEntity {
    private static final long serialVersionUID = -90020699L;
    private Long labelId;
    private String labelIconUrl;
    private String labelIconKey;
    private String labelName;
    /**
     * 文章篇数
     */
    private Integer labelArticleCount;
    /**
     * 关注数量
     */
    private Integer labelFocusCount;
    private Boolean isFocus;

    @Override
    public String toString() {
        return "LabelListVO{" +
                "labelId=" + labelId +
                ", labelIconUrl='" + labelIconUrl + '\'' +
                ", labelIconKey='" + labelIconKey + '\'' +
                ", labelName='" + labelName + '\'' +
                ", labelArticleCount=" + labelArticleCount +
                ", labelFocusCount=" + labelFocusCount +
                ", isFocus=" + isFocus +
                '}';
    }

    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    public String getLabelIconUrl() {
        return labelIconUrl;
    }

    public void setLabelIconUrl(String labelIconUrl) {
        this.labelIconUrl = labelIconUrl;
    }

    public String getLabelIconKey() {
        return labelIconKey;
    }

    public void setLabelIconKey(String labelIconKey) {
        this.labelIconKey = labelIconKey;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public Integer getLabelArticleCount() {
        return labelArticleCount;
    }

    public void setLabelArticleCount(Integer labelArticleCount) {
        this.labelArticleCount = labelArticleCount;
    }

    public Integer getLabelFocusCount() {
        return labelFocusCount;
    }

    public void setLabelFocusCount(Integer labelFocusCount) {
        this.labelFocusCount = labelFocusCount;
    }

    public Boolean getFocus() {
        return isFocus;
    }

    public void setFocus(Boolean focus) {
        isFocus = focus;
    }
}
