package lhb.gdms.commons.domain.entity;

import lhb.gdms.commons.base.persistence.BaseDateEntity;

/**
 * @Description  标签关注表 sys_label_focus 实体类
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/14
 * @time 10:45
 */
public class SysLabelFocusEntity extends BaseDateEntity {

    private static final long serialVersionUID = -90000056L;
    /**
     * id
     */
    private Long sysLabelFocusId;
    /**
     * 标签管理id
     */
    private Long labelId;
    /**
     * 用户id，执行此关注的用户id
     */
    private Long sysUserId;
    /**
     * 关注状态
     * 0-取消关注
     * 1-已关注
     */
    private Integer focusStatus;

    @Override
    public String toString() {
        return "SysLabelFocusEntity{" +
                "sysLabelFocusId=" + sysLabelFocusId +
                ", labelId=" + labelId +
                ", sysUserId=" + sysUserId +
                ", focusStatus=" + focusStatus +
                '}';
    }

    public Long getSysLabelFocusId() {
        return sysLabelFocusId;
    }

    public void setSysLabelFocusId(Long sysLabelFocusId) {
        this.sysLabelFocusId = sysLabelFocusId;
    }

    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    public Long getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Long sysUserId) {
        this.sysUserId = sysUserId;
    }

    public Integer getFocusStatus() {
        return focusStatus;
    }

    public void setFocusStatus(Integer focusStatus) {
        this.focusStatus = focusStatus;
    }
}
