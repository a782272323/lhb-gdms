package lhb.gdms.provider.user.domain.vo;

import java.io.Serializable;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/14
 * @time 02:22
 */
public class RegisteredParamsVO implements Serializable {

    private static final long serialVersionUID = -90000089L;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 手机
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;

    @Override
    public String toString() {
        return "RegisteredParamsVO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
