package lhb.gdms.commons.domain.entity;

import lhb.gdms.commons.base.persistence.BaseDateEntity;

/**
 * @Description  标签管理表 sys_label 实体类
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/14
 * @time 10:43
 */
public class SysLabelEntity extends BaseDateEntity {

    private static final long serialVersionUID = -90000039L;
    /**
     * id
     */
    private Long labelId;
    /**
     * 标签名称
     */
    private String labelName;
    /**
     * 标签头像url
     */
    private String labelIconUrl;
    /**
     * 七牛云key
     */
    private String labelIconKey;

    @Override
    public String toString() {
        return "SysLabelEntity{" +
                "labelId=" + labelId +
                ", labelName='" + labelName + '\'' +
                ", labelIconUrl='" + labelIconUrl + '\'' +
                ", labelIconKey='" + labelIconKey + '\'' +
                '}';
    }

    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
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
}
