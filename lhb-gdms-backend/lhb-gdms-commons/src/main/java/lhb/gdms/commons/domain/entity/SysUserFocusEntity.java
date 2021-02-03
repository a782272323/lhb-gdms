package lhb.gdms.commons.domain.entity;

import lhb.gdms.commons.base.persistence.BaseDateEntity;

/**
 * @Description  用户关注表 sys_user_focus 实体类
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/14
 * @time 10:47
 */
public class SysUserFocusEntity extends BaseDateEntity {
    private static final long serialVersionUID = -90002041L;

    /**
     * id
     */
    private Long sysUserFocusId;
    /**
     * 目标用户id（被关注）
     */
    private Long formSysUserId;
    /**
     * 用户id，执行此关注的用户id
     */
    private Long sysUserId;

    @Override
    public String toString() {
        return "SysUserFocusEntity{" +
                "sysUserFocusId=" + sysUserFocusId +
                ", formSysUserId=" + formSysUserId +
                ", sysUserId=" + sysUserId +
                '}';
    }

    public Long getSysUserFocusId() {
        return sysUserFocusId;
    }

    public void setSysUserFocusId(Long sysUserFocusId) {
        this.sysUserFocusId = sysUserFocusId;
    }

    public Long getFormSysUserId() {
        return formSysUserId;
    }

    public void setFormSysUserId(Long formSysUserId) {
        this.formSysUserId = formSysUserId;
    }

    public Long getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Long sysUserId) {
        this.sysUserId = sysUserId;
    }

}
