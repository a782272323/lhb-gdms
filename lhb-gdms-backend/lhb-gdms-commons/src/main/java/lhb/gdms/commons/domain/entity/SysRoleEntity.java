package lhb.gdms.commons.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lhb.gdms.commons.base.persistence.BaseDateEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description  表 sys_role 实体类
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/21
 * @time 10:03
 */
public class SysRoleEntity extends BaseDateEntity {


    private static final long serialVersionUID = -90019645L;

    /**
     * id
     */
    private Long id;
    /**
     * 角色名称英文
     */
    private String roleName;
    /**
     * 角色名称-中文
     */
    private String roleNameZh;
    /**
     * 描述
     */
    private String roleDescription;
    /**
     * 角色状态
     */
    private Integer roleStatus;

    @Override
    public String toString() {
        return "SysRoleEntity{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", roleNameZh='" + roleNameZh + '\'' +
                ", roleDescription='" + roleDescription + '\'' +
                ", roleStatus=" + roleStatus +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleNameZh() {
        return roleNameZh;
    }

    public void setRoleNameZh(String roleNameZh) {
        this.roleNameZh = roleNameZh;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Integer getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(Integer roleStatus) {
        this.roleStatus = roleStatus;
    }
}
