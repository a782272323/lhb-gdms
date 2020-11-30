package lhb.gdms.commons.domain.entity;

import lhb.gdms.commons.base.persistence.BaseDateEntity;

import java.util.Date;

/**
 * @Description  表 sys_user_icon 系统默认头像表 实体类
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/25
 * @time 00:23
 */
public class SysUserIconEntity extends BaseDateEntity {

    private static final long serialVersionUID = -90000046L;
    /**
     * id
     */
    private Long sysUserIconId;
    /**
     * 头像预览url
     */
    private String userIconUrl;
    /**
     * 七牛云key
     */
    private String key;
    /**
     * 头像昵称
     */
    private String userIconName;
    /**
     * 是否为默认头像
     * 0-不是
     * 1-是默认头像
     */
    private Integer isDefault;

    @Override
    public String toString() {
        return "SysUserIconEntity{" +
                "sysUserIconId=" + sysUserIconId +
                ", userIconUrl='" + userIconUrl + '\'' +
                ", key='" + key + '\'' +
                ", userIconName='" + userIconName + '\'' +
                ", isDefault=" + isDefault +
                '}';
    }

    public Long getSysUserIconId() {
        return sysUserIconId;
    }

    public void setSysUserIconId(Long sysUserIconId) {
        this.sysUserIconId = sysUserIconId;
    }

    public String getUserIconUrl() {
        return userIconUrl;
    }

    public void setUserIconUrl(String userIconUrl) {
        this.userIconUrl = userIconUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserIconName() {
        return userIconName;
    }

    public void setUserIconName(String userIconName) {
        this.userIconName = userIconName;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }
}
