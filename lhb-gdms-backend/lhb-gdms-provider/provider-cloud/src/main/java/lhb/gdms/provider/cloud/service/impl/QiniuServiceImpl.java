package lhb.gdms.provider.cloud.service.impl;


import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lhb.gdms.commons.utils.MapperUtils;
import lhb.gdms.provider.cloud.service.QiniuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

/**
 * @Description  实现类
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/21
 * @time 00:27
 */
@Service
public class QiniuServiceImpl implements QiniuService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
     * 域名,根据区域从官网获取
     */
    @Value("${qiniu.prefix}")
    private String prefix;
    /**
     * 域名前缀
     */
    @Value("${qiniu.domain}")
    private String domain;


    @Autowired
    private BucketManager bucketManager;

    @Autowired
    private UploadManager uploadManager;

    @Autowired
    private Auth auth;

    // 定义七牛云上传的相关策略
    private StringMap putPolicy;



    /**
     * 单文件上传
     * 以流的形式上传
     * @param inputStream
     * @return
     */
    @Override
    public String uploadFile(InputStream inputStream, String key) throws Exception {
        Response response = this.uploadManager.put(inputStream, key, getUploadToken(), null, null);
        // 重试次数
        int retry = 0;
        while (response.needRetry() && retry < 3) {
            response = this.uploadManager.put(inputStream, key, getUploadToken(), null, null);
            retry++;
        }
        // 解析结果
        DefaultPutRet putRet = MapperUtils.json2pojo(response.bodyString(), DefaultPutRet.class);
        logger.debug("putRet = " + putRet);
        String url = domain + "/" + putRet.key;
        logger.debug("上传成功，图片url = " + url);
        return url;
    }

    /**
     * 删除单个文件
     * @param key
     * @return
     * @throws QiniuException
     */
    @Override
    public Response deleteFile(String key) throws Exception {
        Response response = this.bucketManager.delete(bucket, key);
        int retry = 0;
        while (response.needRetry() && retry < 3) {
            response = bucketManager.delete(bucket, key);
            retry++;
        }
        return response;
    }

    /**
     * 获取上传凭证
     * @return
     */
    private String getUploadToken() {
        return this.auth.uploadToken(bucket, null, 3600, putPolicy);
    }
}
