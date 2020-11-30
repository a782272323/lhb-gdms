package lhb.gdms.commons.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lhb.gdms.commons.base.persistence.BaseDateEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description  表 sys_user 实体类
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/21
 * @time 09:56
 */
public class SysUserEntity extends BaseDateEntity {


    private static final long serialVersionUID = -90000022L;

    /**
     * id
     */
    private Long sysUserId;
    /**
     * 昵称
     */
    private String sysUserNickname;
    /**
     * 手机
     */
    private String sysUserPhone;
    /**
     * 邮箱
     */
    private String sysUserEmail;
    /**
     * 地区码
     */
    private String sysUserAreaCode;
    /**
     * 性别
     */
    private String sysUserSex;
    /**
     * 状态
     * 0-禁用
     * 1-启用
     */
    private Integer  sysUserStatus;
    /**
     * 用户名，登录系统使用且不允许修改
     */
    private String sysUserUsername;
    /**
     * 密码，加密
     */
    private String sysUserPassword;
    /**
     * 系统类型
     * 1-门户网站
     * 2-后台管理网站
     * 3-小程序
     */
    private Integer sysUserType;
    /**
     * 用户头像
     */
    private String sysUserIcon;
    /**
     * 七牛云key，若使用系统默认的头像则为空
     */
    private String sysUserKey;
    /**
     * 自我介绍内容
     */
    private String sysUserIntroduction;

    @Override
    public String toString() {
        return "SysUserEntity{" +
                "sysUserId=" + sysUserId +
                ", sysUserNickname='" + sysUserNickname + '\'' +
                ", sysUserPhone='" + sysUserPhone + '\'' +
                ", sysUserEmail='" + sysUserEmail + '\'' +
                ", sysUserAreaCode='" + sysUserAreaCode + '\'' +
                ", sysUserSex='" + sysUserSex + '\'' +
                ", sysUserStatus=" + sysUserStatus +
                ", sysUserUsername='" + sysUserUsername + '\'' +
                ", sysUserPassword='" + sysUserPassword + '\'' +
                ", sysUserType=" + sysUserType +
                ", sysUserIcon='" + sysUserIcon + '\'' +
                ", sysUserKey='" + sysUserKey + '\'' +
                ", sysUserIntroduction='" + sysUserIntroduction + '\'' +
                '}';
    }

    public Long getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Long sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getSysUserNickname() {
        return sysUserNickname;
    }

    public void setSysUserNickname(String sysUserNickname) {
        this.sysUserNickname = sysUserNickname;
    }

    public String getSysUserPhone() {
        return sysUserPhone;
    }

    public void setSysUserPhone(String sysUserPhone) {
        this.sysUserPhone = sysUserPhone;
    }

    public String getSysUserEmail() {
        return sysUserEmail;
    }

    public void setSysUserEmail(String sysUserEmail) {
        this.sysUserEmail = sysUserEmail;
    }

    public String getSysUserAreaCode() {
        return sysUserAreaCode;
    }

    public void setSysUserAreaCode(String sysUserAreaCode) {
        this.sysUserAreaCode = sysUserAreaCode;
    }

    public String getSysUserSex() {
        return sysUserSex;
    }

    public void setSysUserSex(String sysUserSex) {
        this.sysUserSex = sysUserSex;
    }

    public Integer getSysUserStatus() {
        return sysUserStatus;
    }

    public void setSysUserStatus(Integer sysUserStatus) {
        this.sysUserStatus = sysUserStatus;
    }

    public String getSysUserUsername() {
        return sysUserUsername;
    }

    public void setSysUserUsername(String sysUserUsername) {
        this.sysUserUsername = sysUserUsername;
    }

    public String getSysUserPassword() {
        return sysUserPassword;
    }

    public void setSysUserPassword(String sysUserPassword) {
        this.sysUserPassword = sysUserPassword;
    }

    public Integer getSysUserType() {
        return sysUserType;
    }

    public void setSysUserType(Integer sysUserType) {
        this.sysUserType = sysUserType;
    }

    public String getSysUserIcon() {
        return sysUserIcon;
    }

    public void setSysUserIcon(String sysUserIcon) {
        this.sysUserIcon = sysUserIcon;
    }

    public String getSysUserKey() {
        return sysUserKey;
    }

    public void setSysUserKey(String sysUserKey) {
        this.sysUserKey = sysUserKey;
    }

    public String getSysUserIntroduction() {
        return sysUserIntroduction;
    }

    public void setSysUserIntroduction(String sysUserIntroduction) {
        this.sysUserIntroduction = sysUserIntroduction;
    }
}
