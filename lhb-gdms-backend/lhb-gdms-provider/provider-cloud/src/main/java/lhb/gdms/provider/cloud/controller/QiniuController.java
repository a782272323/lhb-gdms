package lhb.gdms.provider.cloud.controller;

import com.google.common.collect.Maps;
import com.qiniu.http.Response;
import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.constant.ResponseConstant;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.provider.cloud.service.QiniuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("/cloud/qiniu/delete/one/{key}")
    public Response deleteOne(@PathVariable("key") String key) throws Exception{
        Map<String, Object> map = Maps.newHashMap();
        Response response = qiniuService.deleteFile(key);
        map.put("response", response);
        return response;
    }
}
