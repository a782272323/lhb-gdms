package lhb.gdms.commons.domain.dto;

import java.io.Serializable;

/**
 * @Description  阿里云短信实体
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/10
 * @time 12:22
 */
public class AliyunSmsDTO implements Serializable {

    private static final long serialVersionUID = -96084861L;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 短信模版参数
     * 格式: "{\"code\":\"123465\"}"
     */
    private String templateParam;

    private String outId;

    /**
     * 阿里云短信模版code
     */
    private String templateCode;

    @Override
    public String toString() {
        return "AliyunSmsDTO{" +
                "phone='" + phone + '\'' +
                ", templateParam='" + templateParam + '\'' +
                ", outId='" + outId + '\'' +
                ", templateCode='" + templateCode + '\'' +
                '}';
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(String templateParam) {
        this.templateParam = templateParam;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }
}
