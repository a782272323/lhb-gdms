package lhb.gdms.provider.cloud.config;


import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description  七牛云上传服务配置
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/21
 * @time 00:24
 */
@Configuration
public class QiniuUploadConfig {

    /**
     * 密钥
     */
    @Value("${qiniu.accessKey}")
    private String accessKey;
    /**
     * 密钥
     */
    @Value("${qiniu.secretKey}")
    private String secretKey;
    /**
     * 密码空间
     */
    @Value("${qiniu.bucket}")
    private String bucket;
    /**
     * 域名
     */
    @Value("${qiniu.prefix}")
    private String prefix;
    /**
     * 存储区域
     */
    @Value("${qiniu.zone}")
    private String zone;

//    /**
//     * 注入存储空间所在的机房区域
//     * z0-华东
//     * z1-华北
//     * z2-华南
//     * na0-北美
//     * as0-东南亚
//     * @return
//     */
//    @Bean
//    public com.qiniu.storage.Configuration qiniuConfig() {
//        return new com.qiniu.storage.Configuration(Zone.zone2());
//    }

    /**
     * 配置空间的存储区域
     * @return
     */
    @Bean
    public com.qiniu.storage.Configuration qiniuConfig() throws Exception {
        switch (zone) {
            case "huadong":
                return new com.qiniu.storage.Configuration(Zone.huadong());
            case "huabei":
                return new com.qiniu.storage.Configuration(Zone.huabei());
            case "huanan":
                return new com.qiniu.storage.Configuration(Zone.huanan());
            case "beimei":
                return new com.qiniu.storage.Configuration(Zone.beimei());
            default:
                throw new Exception();
        }
    }

    /**
     * 构建一个七牛上传工具实例
     * @return
     */
    @Bean
    public UploadManager uploadManager() throws Exception {
        return new  UploadManager(qiniuConfig());
    }

    /**
     * 认证信息实例
     * @return
     */
    @Bean
    public Auth auth() {
        return Auth.create(accessKey, secretKey);
    }

    /**
     * 构建七牛空间管理实例
     * @return
     */
    @Bean
    public BucketManager bucketManager() throws Exception {
        return new BucketManager(auth(), qiniuConfig());
    }


}
