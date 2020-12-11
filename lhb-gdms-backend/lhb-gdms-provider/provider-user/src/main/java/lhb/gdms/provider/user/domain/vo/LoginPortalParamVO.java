package lhb.gdms.provider.user.domain.vo;

import java.io.Serializable;

/**
 * @Description  门户网站登录参数 VO
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/10
 * @time 00:25
 */
public class LoginPortalParamVO implements Serializable {

    private static final long serialVersionUID = -90000078L;
    /**
     * 登录类型
     * 1-密码登录
     * 2-手机登录
     * 3-邮箱登录
     */
    private Integer loginType;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 手机验证码
     */
    private String phoneCode;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 邮箱验证码
     */
    private String emailCode;

    @Override
    public String toString() {
        return "LoginPortalParamVO{" +
                "loginType='" + loginType + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", phoneCode='" + phoneCode + '\'' +
                ", email='" + email + '\'' +
                ", emailCode='" + emailCode + '\'' +
                '}';
    }

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailCode() {
        return emailCode;
    }

    public void setEmailCode(String emailCode) {
        this.emailCode = emailCode;
    }
}
