package lhb.gdms.feign.cloud;

import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.configuration.aop.config.PrintlnLog;
import lhb.gdms.feign.cloud.fallback.Oauth2FeignFallback;
import lhb.gdms.feign.cloud.fallback.QiniuFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description  七牛云文件服务feign远程调用
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/25
 * @time 12:58
 */
@Component
@FeignClient(name = "provider-cloud", fallback = QiniuFeignFallback.class)
public interface QiniuFeign {



    /**
     * 单文件上传
     * 以流的形式上传
     * @param file
     * @return
     */
    @PostMapping(value = "/cloud/qiniu/upload/one", consumes = {"multipart/form-data"})
    public BaseResult uploadOne(@RequestPart("file") MultipartFile file,
                                @RequestParam("key") String key) throws Exception;


    /**
     * 删除单个文件
     * @param key
     * @return
     */
    @PostMapping("/cloud/qiniu/delete/one")
    public BaseResult deleteOne(@RequestParam("key")String key) throws Exception;

    /**
     * 重命名七牛云文件名
     * @param oldKey
     * @param newKey
     * @return
     * @throws Exception
     */
    @PostMapping("/cloud/qiniu/rename")
    public BaseResult rename(@RequestParam("oldKey") String oldKey,
                             @RequestParam("newKey") String newKey) throws Exception;

    /**
     * 测试通用服务feign远程调用
     * @param
     * @return
     */
    @GetMapping("/cloud/test")
    public BaseResult test();
}
