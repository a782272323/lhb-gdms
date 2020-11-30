package lhb.gdms.provider.user.domain.vo;

import java.io.Serializable;

/**
 * @Description  与前端交互的登录参数
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/22
 * @time 10:42
 */
public class LoginParamVO implements Serializable {

    private static final long serialVersionUID = -90002985L;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    @Override
    public String toString() {
        return "LoginParamVO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
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
}
