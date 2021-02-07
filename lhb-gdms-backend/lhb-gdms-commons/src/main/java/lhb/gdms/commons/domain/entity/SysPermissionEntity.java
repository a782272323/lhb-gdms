package lhb.gdms.commons.domain.entity;

import lhb.gdms.commons.base.persistence.BaseDateEntity;

/**
 * @Description  系统资源表 sys_permission 实体类
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/25
 * @time 00:32
 */
public class SysPermissionEntity extends BaseDateEntity {

    private static final long serialVersionUID = -93404679L;
    /**
     * id
     */
    private Long permissionId;
    /**
     * 描述
     */
    private String permissionDescription;
    /**
     * 资源名称-英文
     */
    private String permissionName;
    /**
     * 资源名称-中文
     */
    private String permissionNameZh;
    /**
     * 资源状态
     * 0-禁用
     * 1-开启
     */
    private Integer permissionStatus;
    /**
     * 上一级资源(父级）
     */
    private Long parentId;

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionDescription() {
        return permissionDescription;
    }

    public void setPermissionDescription(String permissionDescription) {
        this.permissionDescription = permissionDescription;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionNameZh() {
        return permissionNameZh;
    }

    public void setPermissionNameZh(String permissionNameZh) {
        this.permissionNameZh = permissionNameZh;
    }

    public Integer getPermissionStatus() {
        return permissionStatus;
    }

    public void setPermissionStatus(Integer permissionStatus) {
        this.permissionStatus = permissionStatus;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "SysPermissionEntity{" +
                "permissionId=" + permissionId +
                ", permissionDescription='" + permissionDescription + '\'' +
                ", permissionName='" + permissionName + '\'' +
                ", permissionNameZh='" + permissionNameZh + '\'' +
                ", permissionStatus=" + permissionStatus +
                ", parentId=" + parentId +
                '}';
    }
}
