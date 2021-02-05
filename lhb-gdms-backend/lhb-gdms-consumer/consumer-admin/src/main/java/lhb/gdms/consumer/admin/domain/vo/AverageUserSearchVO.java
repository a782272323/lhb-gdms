package lhb.gdms.consumer.admin.domain.vo;

import java.io.Serializable;

/**
 * @Description  
 * @author Herbie Leung(梁鸿斌)
 * @date 2021/2/3
 * @time 23:10
 */
public class AverageUserSearchVO implements Serializable {
    private static final long serialVersionUID = -93406776L;
    private String sysUserNickname;
    private String sysUserUsername;
    private String sysUserPhone;
    private String sysUserEmail;

    @Override
    public String toString() {
        return "AverageUserSearchVO{" +
                "sysUserNickname='" + sysUserNickname + '\'' +
                ", sysUserUsername='" + sysUserUsername + '\'' +
                ", sysUserPhone='" + sysUserPhone + '\'' +
                ", sysUserEmail='" + sysUserEmail + '\'' +
                '}';
    }

    public String getSysUserNickname() {
        return sysUserNickname;
    }

    public void setSysUserNickname(String sysUserNickname) {
        this.sysUserNickname = sysUserNickname;
    }

    public String getSysUserUsername() {
        return sysUserUsername;
    }

    public void setSysUserUsername(String sysUserUsername) {
        this.sysUserUsername = sysUserUsername;
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
}
