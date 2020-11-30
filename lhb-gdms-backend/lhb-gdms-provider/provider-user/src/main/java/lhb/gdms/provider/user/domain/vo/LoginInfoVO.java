package lhb.gdms.provider.user.domain.vo;

import java.io.Serializable;

/**
 * @Description  登录成功后跳转首页用的用户信息参数
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/22
 * @time 20:42
 */
public class LoginInfoVO implements Serializable {


    private static final long serialVersionUID = -90014519L;
    /**
     * 用户名
     */
    private String name;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 角色
     */
    private String roles;

    @Override
    public String toString() {
        return "LoginInfoVO{" +
                "name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", roles='" + roles + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
