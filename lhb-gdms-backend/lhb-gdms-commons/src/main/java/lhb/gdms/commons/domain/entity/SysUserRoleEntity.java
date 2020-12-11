package lhb.gdms.commons.domain.entity;

import lhb.gdms.commons.base.persistence.BaseDateEntity;

/**
 * @Description  表 sys_user_role 用户角色关系表 实体类
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/9
 * @time 17:53
 */
public class SysUserRoleEntity extends BaseDateEntity {


    private static final long serialVersionUID = -2510676449490474682L;
    /**
     * id
     */
    private Long sysUserRoleId;
    /**
     * 用户id
     */
    private Long sysUserId;
    /**
     * 角色id
     */
    private Long roleId;

    @Override
    public String toString() {
        return "SysUserRoleEntity{" +
                "sysUserRoleId=" + sysUserRoleId +
                ", sysUserId=" + sysUserId +
                ", roleId=" + roleId +
                '}';
    }

    public Long getSysUserRoleId() {
        return sysUserRoleId;
    }

    public void setSysUserRoleId(Long sysUserRoleId) {
        this.sysUserRoleId = sysUserRoleId;
    }

    public Long getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Long sysUserId) {
        this.sysUserId = sysUserId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
