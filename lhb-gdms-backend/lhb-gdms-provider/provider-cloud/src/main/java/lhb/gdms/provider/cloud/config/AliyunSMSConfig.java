package lhb.gdms.provider.cloud.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description  阿里云短信服务配置类
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/10
 * @time 12:19
 */
@Component
@ConfigurationProperties(prefix = "aliyun.sms")
public class AliyunSMSConfig {

    /**
     * 阿里云 accessKeyId
     */
    private String accessKeyId;
    /**
     * 阿里云 accessKeySecret
     */
    private String accessKeySecret;
    /**
     * 产品名称:云通信短信api产品
     */
    private String product;
    /**
     * 产品域名
     */
    private String domain;
    private String regionId;
    /**
     * 短信签名
     */
    private String signName;
    /**
     * 签名算法版本 固定
     */
    private String version;
    /**
     * 请求 api 的名称
     */
    private String action;
    /**
     * 发送日期 支持30天内记录查询 格式yyyyMMdd
     */
    private String dateFormat;
    /**
     * 服务结点
     */
    private String endpointName;
    /**
     * 短信认证模版code
     * SMS_205810298
     */
    private String templateCode01;


    @Override
    public String toString() {
        return "AliyunSMSConfig{" +
                "accessKeyId='" + accessKeyId + '\'' +
                ", accessKeySecret='" + accessKeySecret + '\'' +
                ", product='" + product + '\'' +
                ", domain='" + domain + '\'' +
                ", regionId='" + regionId + '\'' +
                ", signName='" + signName + '\'' +
                ", version='" + version + '\'' +
                ", action='" + action + '\'' +
                ", dateFormat='" + dateFormat + '\'' +
                ", endpointName='" + endpointName + '\'' +
                ", templateCode01='" + templateCode01 + '\'' +
                '}';
    }

    public String getTemplateCode01() {
        return templateCode01;
    }

    public void setTemplateCode01(String templateCode01) {
        this.templateCode01 = templateCode01;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getEndpointName() {
        return endpointName;
    }

    public void setEndpointName(String endpointName) {
        this.endpointName = endpointName;
    }
}
