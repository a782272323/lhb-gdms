package lhb.gdms.provider.cloud.service;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;

import java.io.File;
import java.io.InputStream;

/**
 * @Description  七牛云文件上传服务
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/21
 * @time 00:25
 */
public interface QiniuService {

    /**
     * 单文件上传
     * 以流的形式上传
     * @param inputStream
     * @param key
     * @return
     * @throws Exception
     */
    String uploadFile(InputStream inputStream, String key) throws Exception;

    /**
     * 文件上传
     * 以文件的形式上传
     * @param file
     * @param key
     * @return
     * @throws Exception
     */
    String uploadFile(File file, String key) throws Exception;

    /**
     * 删除单个文件
     * @param key
     * @return
     * @throws QiniuException
     */
    String deleteFile(String key) throws QiniuException;

    /**
     * 重命名文件
     * @param oldKey
     * @param newKey
     * @return
     * @throws Exception
     */
    String renameFile(String oldKey, String newKey) throws Exception;
}
