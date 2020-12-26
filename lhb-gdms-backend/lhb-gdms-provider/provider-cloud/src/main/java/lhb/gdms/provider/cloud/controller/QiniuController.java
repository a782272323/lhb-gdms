package lhb.gdms.provider.cloud.controller;

import com.google.common.collect.Maps;
import com.qiniu.http.Response;
import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.constant.ResponseConstant;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.configuration.aop.config.PrintlnLog;
import lhb.gdms.provider.cloud.service.QiniuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @Description  七牛controller
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/21
 * @time 00:30
 */
@RestController
@CrossOrigin
public class QiniuController {

    private final Logger logger  = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QiniuService qiniuService;

    /**
     * 单文件上传
     * 以流的形式上传
     * @param file
     * @return
     */
    @PostMapping("/cloud/qiniu/upload/one")
    public BaseResult uploadOne(@RequestPart("file") MultipartFile file,
                                @RequestParam("key") String key) throws Exception{
        Map<String, Object> map = Maps.newHashMap();
        map.put("url", qiniuService.uploadFile(file.getInputStream(), key));
        map.put("key", key);
        return BaseResult.ok().put(
                HttpConstant.OK,
                HttpConstant.UPLOAD_MESSAGE,
                ResponseConstant.DATA,
                map);
    }


    /**
     * 删除单个文件
     * @param key
     * @return
     */
    @PrintlnLog(description = "根据key删除七牛云的图片-controller")
    @PostMapping("/cloud/qiniu/delete/one")
    public BaseResult deleteOne(@RequestParam("key")String key) throws Exception {
        qiniuService.deleteFile(key);
        return BaseResult.ok(HttpConstant.OK, HttpConstant.DELETE_MESSAGE);
//        return HttpConstant.DELETE_MESSAGE.equals(qiniuService.deleteFile(key))
//                ? BaseResult.ok(HttpConstant.OK, HttpConstant.DELETE_MESSAGE)
//                : BaseResult.error(HttpConstant.ERROR, HttpConstant.DELETE_ERROR_MESSAGE);
    }

    /**
     * 重命名文件
     * @param oldKey
     * @param newKey
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "重命名文件-controller")
    @PostMapping("/cloud/qiniu/rename")
    public BaseResult rename(@RequestParam("oldKey") String oldKey,
                             @RequestParam("newKey") String newKey) throws Exception{
        logger.debug("oldKey = " + oldKey, ",,,newKey = " + newKey);
        return BaseResult.ok(qiniuService.renameFile(oldKey, newKey));
    }

    @GetMapping("/cloud/test")
    public BaseResult test() {
        return BaseResult.ok();
    }
}
