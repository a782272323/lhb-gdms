package lhb.gdms.commons.domain.dto;

import sun.dc.pr.PRError;

import java.io.Serializable;

/**
 * @Description  用于登录获取token的参数
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/22
 * @time 00:06
 */
public class Oauth2DTO implements Serializable {


    private static final long serialVersionUID = -90000010L;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 客户端id
     */
    private String client_id;
    /**
     * 客户端密钥
     */
    private String client_secret;
    /**
     * 授权模式
     */
    private String grant_type;
    /**
     * 授权范围
     */
    private String scope;

    @Override
    public String toString() {
        return "Oauth2DTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", client_id='" + client_id + '\'' +
                ", client_secret='" + client_secret + '\'' +
                ", grant_type='" + grant_type + '\'' +
                ", scope='" + scope + '\'' +
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

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
