package lhb.gdms.commons.domain.entity;

import lhb.gdms.commons.base.persistence.BaseDateEntity;

import java.io.Serializable;

/**
 * @Description  表 sys_user_phone_email(角色邮箱手机验证关系表) 实体类
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/9
 * @time 18:11
 */
public class SysUserPhoneEmailEntity implements Serializable {

    private static final long serialVersionUID = -90000038L;
    /**
     * id
     */
    private Long sysUserPhoneEmailId;
    /**
     * 是否完成手机验证,默认未认证.0-未认证,1-已认证
     */
    private Integer isPhone;
    /**
     * 是否完成邮箱验证,默认未认证.0-未认证,1-已认证
     */
    private Integer isEmail;
    /**
     * 用户id
     */
    private Long sysUserId;

    @Override
    public String toString() {
        return "SysUserPhoneEmailEntity{" +
                "sysUserPhoneEmailId=" + sysUserPhoneEmailId +
                ", isPhone=" + isPhone +
                ", isEmail=" + isEmail +
                ", sysUserId=" + sysUserId +
                '}';
    }

    public Long getSysUserPhoneEmailId() {
        return sysUserPhoneEmailId;
    }

    public void setSysUserPhoneEmailId(Long sysUserPhoneEmailId) {
        this.sysUserPhoneEmailId = sysUserPhoneEmailId;
    }

    public Integer getIsPhone() {
        return isPhone;
    }

    public void setIsPhone(Integer isPhone) {
        this.isPhone = isPhone;
    }

    public Integer getIsEmail() {
        return isEmail;
    }

    public void setIsEmail(Integer isEmail) {
        this.isEmail = isEmail;
    }

    public Long getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Long sysUserId) {
        this.sysUserId = sysUserId;
    }
}
