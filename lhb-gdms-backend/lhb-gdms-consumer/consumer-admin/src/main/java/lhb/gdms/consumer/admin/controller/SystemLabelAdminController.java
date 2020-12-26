package lhb.gdms.consumer.admin.controller;

import com.google.common.collect.Maps;
import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.constant.ResponseConstant;
import lhb.gdms.commons.domain.entity.SysLabelEntity;
import lhb.gdms.commons.domain.vo.QueryVO;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.commons.utils.ImageUtils;
import lhb.gdms.commons.utils.TimeUtils;
import lhb.gdms.configuration.aop.config.PrintlnLog;
import lhb.gdms.consumer.admin.mapper.SysLabelMapper;
import lhb.gdms.consumer.admin.service.SysLabelService;
import lhb.gdms.feign.cloud.QiniuFeign;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

/**
 * @author Herbie Leung(梁鸿斌)
 * @Description 标签管理 controller
 * @date 2020/12/14
 * @time 10:29
 */
@RestController
@CrossOrigin
public class SystemLabelAdminController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysLabelService sysLabelService;

    @Autowired
    private SysLabelMapper sysLabelMapper;

    @Autowired
    private QiniuFeign qiniuFeign;

    /**
     * 新增标签
     *
     * @param labelName
     * @param file
     * @return
     */
    @PostMapping("/admin/system/label")
    public BaseResult insertLabel(String labelName,
                                  @RequestPart("file") MultipartFile file) throws Exception {
        logger.debug(labelName);
        if (StringUtils.isBlank(labelName)) {
            return BaseResult.error("标签名称不能为空");
        }
        // 检测头像昵称是重复
        if (sysLabelService.checkName(labelName)) {
            return BaseResult.error("标签名称已存在");
        }
        // 校验图片格式
        if (ImageUtils.isImgFormat(file, "jpg").equals("文件名不正确") &&
                ImageUtils.isImgFormat(file, "png").equals("文件名不正确")) {
            return BaseResult.error(HttpConstant.ERROR, "文件名不正确");
        }
        if (ImageUtils.isImgFormat(file, "jpg").equals("图片不正常") &&
                ImageUtils.isImgFormat(file, "png").equals("图片不正常")) {
            return BaseResult.error(HttpConstant.ERROR, "文件只支持jpg或png格式");
        }
        // 校验图片大小
        if (ImageUtils.imgSizeLimitMax(5, file).equals("图片正常") == false) {
            return BaseResult.error("图片大小不能超过" + 5 + "M");
        }

        // 生成七牛云key规则
        String date = DateTimeFormatter.ofPattern(TimeUtils.YYYY_MM_DD_AND_HH_MM_SS).format(LocalDateTime.now());
        String labelIconKey = "lhb-gdms/sys-label/" + date + "/" + labelName;

        // 上传图片到七牛云
        BaseResult qiniuResult = qiniuFeign.uploadOne(file, labelIconKey);
        if (qiniuResult.get("code").toString().equals("200") == false) {
            return BaseResult.error(HttpConstant.ERROR_MESSAGE);
        }
        Map<String, Object> map = (Map<String, Object>) qiniuResult.get("data");
        String labelIconUrl = map.get("url").toString();
        // 封装参数
        SysLabelEntity labelEntity = new SysLabelEntity();
        labelEntity.setLabelIconUrl(labelIconUrl);
        labelEntity.setLabelIconKey(labelIconKey);
        labelEntity.setLabelName(labelName);
        labelEntity.setCreated(new Date());
        labelEntity.setUpdated(new Date());
        logger.debug("labelEntity = " + labelEntity.toString());

        if (sysLabelService.insert(labelEntity) > 0) {
            return BaseResult.ok(HttpConstant.SAVE_MESSAGE);
        } else {
            // 删除图片
            if (qiniuFeign.deleteOne(labelIconKey).get("code").toString().equals("200")) {
                logger.debug("删除图片成功");
            }
        }

        return BaseResult.error(HttpConstant.ERROR_MESSAGE);
    }

    /**
     * 编辑标签头像
     *
     * @param file
     * @param labelId
     * @return
     */
    @PostMapping("/admin/system/label/icon/{labelId}")
    public BaseResult updateLabelIcon(@RequestPart("file") MultipartFile file,
                                      @PathVariable("labelId") Long labelId) throws Exception {
        logger.debug(file.getOriginalFilename());
        // 校验图片格式
        if (ImageUtils.isImgFormat(file, "jpg").equals("文件名不正确") &&
                ImageUtils.isImgFormat(file, "png").equals("文件名不正确")) {
            return BaseResult.error(HttpConstant.ERROR, "文件名不正确");
        }
        if (ImageUtils.isImgFormat(file, "jpg").equals("图片不正常") &&
                ImageUtils.isImgFormat(file, "png").equals("图片不正常")) {
            return BaseResult.error(HttpConstant.ERROR, "文件只支持jpg或png格式");
        }
        // 校验图片大小
        if (ImageUtils.imgSizeLimitMax(5, file).equals("图片正常") == false) {
            return BaseResult.error("图片大小不能超过" + 5 + "M");
        }

        SysLabelEntity labelEntity = new SysLabelEntity();
        labelEntity.setLabelId(labelId);
        SysLabelEntity sysLabelEntity = sysLabelService.selectOneById(labelEntity);
        BaseResult qiniuDeleteResult = qiniuFeign.deleteOne(sysLabelEntity.getLabelIconKey());
        logger.debug(qiniuDeleteResult.toString());
        // 先删除旧的图片再添加新的
        if (qiniuDeleteResult.get("code").toString().equals("200") == false) {
            return BaseResult.error(HttpConstant.ERROR_MESSAGE);
        }
        logger.debug("删除成功");
        String date = DateTimeFormatter.ofPattern(TimeUtils.YYYY_MM_DD_AND_HH_MM_SS).format(LocalDateTime.now());
        String key = "lhb-gdms/sys-label/test/" + date + "/" + sysLabelEntity.getLabelName();
        // 上传图片到七牛云
        BaseResult qiniuResult = qiniuFeign.uploadOne(file, key);
        if (qiniuResult.get("code").toString().equals("200") == false) {
            return BaseResult.error(HttpConstant.ERROR_MESSAGE);
        }
        Map<String, Object> map = (Map<String, Object>) qiniuResult.get("data");
        String labelIconUrl = map.get("url").toString();
        sysLabelEntity.setLabelIconKey(key);
        sysLabelEntity.setLabelIconUrl(labelIconUrl);
        sysLabelEntity.setUpdated(new Date());
        logger.debug(sysLabelEntity.toString());
        if (sysLabelService.update(sysLabelEntity) > 0) {
            return BaseResult.ok("编辑成功");
        }

        return BaseResult.error(HttpConstant.ERROR_MESSAGE);
    }

    /**
     * 修改标签名称
     *
     * @param labelId
     * @param labelName
     * @return
     */
    @PrintlnLog(description = "修改标签名称-controller")
    @PostMapping("/admin/system/label/name/{labelId}")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public BaseResult updateLabelName(@PathVariable("labelId") Long labelId,
                                      String labelName) throws Exception {
        SysLabelEntity labelEntity = new SysLabelEntity();
        labelEntity.setLabelId(labelId);
        SysLabelEntity entity = sysLabelService.selectOneById(labelEntity);
        // 重复性校验
        // 检测头像昵称是重复
        if (sysLabelService.checkNameByUpdate(labelName, labelId)) {
            return BaseResult.error("标签名称已存在");
        }

        // 旧的文件名
        String oldKey = entity.getLabelIconKey();
        // 新的文件名
        String date = DateTimeFormatter.ofPattern(TimeUtils.YYYY_MM_DD_AND_HH_MM_SS).format(LocalDateTime.now());
        String newKey = "lhb-gdms/sys-label/" + date + "/" + labelName;
        String url = "http://img.782272323.cn/" + newKey;
        // 封装参数
        entity.setLabelName(labelName);
        entity.setLabelIconKey(newKey);
        entity.setLabelIconUrl(url);
        entity.setUpdated(new Date());
        if (sysLabelService.updateLabelName(entity) > 0) {
            qiniuFeign.rename(oldKey, newKey);
            return BaseResult.ok(HttpConstant.EDIT_MESSAGE);
        }
        return BaseResult.error(HttpConstant.ERROR_MESSAGE);
    }

    /**
     * 删除标签
     * @param labelId
     * @return
     */
    @PrintlnLog(description = "删除标签-controller")
    @DeleteMapping("/admin/system/label/{labelId}")
    public BaseResult delete(@PathVariable("labelId") Long labelId) throws Exception {
        SysLabelEntity labelEntity = new SysLabelEntity();
        labelEntity.setLabelId(labelId);
        SysLabelEntity entity = sysLabelService.selectOneById(labelEntity);

        // todo 查看是否有人使用或者有文章
        if (sysLabelService.deleteOneByKeyWord(labelEntity) > 0) {
            qiniuFeign.deleteOne(entity.getLabelIconKey());
            return BaseResult.ok(HttpConstant.DELETE_MESSAGE);
        }
        return BaseResult.error(HttpConstant.ERROR_MESSAGE);
    }

    /**
     * 获取全部列表信息
     * @param queryVO
     * @return
     */
    @PrintlnLog(description = "获取全部列表信息-controller")
    @GetMapping("/admin/system/label/lists")
    public BaseResult getLists(QueryVO queryVO) {
        if (("labelName").equals(queryVO.getKeyWord().trim())) {
            queryVO.setKeyWord("label_name");
        }
        if (StringUtils.isBlank(queryVO.getKeyWord())) {
            queryVO.setKeyWord("label_name");
        }
        if (StringUtils.isBlank(queryVO.getOrderBy())) {
            queryVO.setOrderBy("ASC");
        }
        Map<String, Object> map = Maps.newHashMap();
        map.put("getLists", sysLabelMapper.selectPageHelper(queryVO));
        map.put("count", sysLabelMapper.selectPageHelperCount());
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

    /**
     * 模糊查询
     * @param queryVO
     * @param labelName
     * @return
     */
    @PrintlnLog(description = "模糊查询-controller")
    @GetMapping("/admin/system/label/lists/query")
    public BaseResult query(QueryVO queryVO, String labelName) {
        if (("labelName").equals(queryVO.getKeyWord().trim())) {
            queryVO.setKeyWord("label_name");
        }
        if (StringUtils.isBlank(queryVO.getKeyWord())) {
            queryVO.setKeyWord("label_name");
        }
        if (StringUtils.isBlank(queryVO.getOrderBy())) {
            queryVO.setOrderBy("ASC");
        }
        Map<String, Object> map = Maps.newHashMap();
        map.put("getLists", sysLabelMapper.query(queryVO, labelName));
        map.put("count", sysLabelMapper.queryCount(labelName));
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }
}
