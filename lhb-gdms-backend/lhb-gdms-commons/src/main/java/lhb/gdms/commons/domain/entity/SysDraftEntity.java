package lhb.gdms.commons.domain.entity;

import lhb.gdms.commons.base.persistence.BaseDateEntity;

/**
 * @Description  表 sys_draft 草稿箱表 实体类
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/22
 * @time 01:30
 */
public class SysDraftEntity extends BaseDateEntity {

    private static final long serialVersionUID = -90002709L;
    /**
     * id
     */
    private Long sysDraftId;
    /**
     * 标题,在草稿箱中可以为空，为空则默认为自定义标题
     */
    private String draftTitle;
    /**
     * 内容
     */
    private String draftContent;
    /**
     * 文章类型
     */
    private String draftType;
    /**
     * 文章封面大图
     */
    private String draftImgUrl;
    /**
     * 七牛云key
     */
    private String draftImgKey;
    /**
     * 用户id
     */
    private Long sysUserId;

    @Override
    public String toString() {
        return "SysDraftEntity{" +
                "sysDraftId=" + sysDraftId +
                ", draftTitle='" + draftTitle + '\'' +
                ", draftContent='" + draftContent + '\'' +
                ", draftType='" + draftType + '\'' +
                ", draftImgUrl='" + draftImgUrl + '\'' +
                ", draftImgKey='" + draftImgKey + '\'' +
                ", sysUserId=" + sysUserId +
                '}';
    }

    public String getDraftType() {
        return draftType;
    }

    public void setDraftType(String draftType) {
        this.draftType = draftType;
    }

    public Long getSysDraftId() {
        return sysDraftId;
    }

    public void setSysDraftId(Long sysDraftId) {
        this.sysDraftId = sysDraftId;
    }

    public String getDraftTitle() {
        return draftTitle;
    }

    public void setDraftTitle(String draftTitle) {
        this.draftTitle = draftTitle;
    }

    public String getDraftContent() {
        return draftContent;
    }

    public void setDraftContent(String draftContent) {
        this.draftContent = draftContent;
    }

    public String getDraftImgUrl() {
        return draftImgUrl;
    }

    public void setDraftImgUrl(String draftImgUrl) {
        this.draftImgUrl = draftImgUrl;
    }

    public String getDraftImgKey() {
        return draftImgKey;
    }

    public void setDraftImgKey(String draftImgKey) {
        this.draftImgKey = draftImgKey;
    }

    public Long getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Long sysUserId) {
        this.sysUserId = sysUserId;
    }
}
