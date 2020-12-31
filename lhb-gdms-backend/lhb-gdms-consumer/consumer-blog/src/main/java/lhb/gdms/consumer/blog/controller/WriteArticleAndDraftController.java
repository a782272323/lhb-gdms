package lhb.gdms.consumer.blog.controller;

import com.google.common.collect.Maps;
import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.constant.ResponseConstant;
import lhb.gdms.commons.domain.entity.SysArticleEntity;
import lhb.gdms.commons.domain.entity.SysDraftEntity;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.commons.utils.TimeUtils;
import lhb.gdms.configuration.aop.config.PrintlnLog;
import lhb.gdms.configuration.utils.SecurityOauth2Utils;
import lhb.gdms.consumer.blog.domain.cover.SysArticleCover;
import lhb.gdms.consumer.blog.domain.cover.SysDraftCover;
import lhb.gdms.consumer.blog.domain.vo.ArticleVO;
import lhb.gdms.consumer.blog.mapper.OtherMapper;
import lhb.gdms.consumer.blog.mapper.SysDraftMapper;
import lhb.gdms.consumer.blog.service.SysArticleService;
import lhb.gdms.consumer.blog.service.SysDraftService;
import lhb.gdms.feign.cloud.QiniuFeign;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description  写文章和草稿箱的 controller
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/22 
 * @time 01:27
 */
@RestController
@CrossOrigin
public class WriteArticleAndDraftController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QiniuFeign qiniuFeign;

    @Autowired
    private SecurityOauth2Utils securityOauth2Utils;

    @Autowired
    private SysDraftService sysDraftService;

    @Autowired
    private OtherMapper otherMapper;

    @Autowired
    private SysArticleService sysArticleService;

    @Autowired
    private SysDraftMapper sysDraftMapper;

    /**
     * 用户写文章内容时上传的图片
     * @param authentication
     * @return
     * @throws Exception
     */
    @PostMapping("/blog/write/article/content/img")
    public BaseResult articleContentImg(Authentication authentication,
                                        @RequestPart("file")MultipartFile file) throws Exception{
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        String date = DateTimeFormatter.ofPattern(TimeUtils.YYYY_MM_DD_AND_HH_MM_SS).format(LocalDateTime.now());
        String key = "lhb-gdms/sys-article/img/" + sysUserId + "/" + date;

        return qiniuFeign.uploadOne(file, key);
    }

    /**
     * 添加文章封面图片
     * @param authentication
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/blog/write/article/cover/img")
    public BaseResult articleCoverImg(Authentication authentication,
                                      @RequestPart("file")MultipartFile file) throws Exception{
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        String date = DateTimeFormatter.ofPattern(TimeUtils.YYYY_MM_DD_AND_HH_MM_SS).format(LocalDateTime.now());
        String key = "lhb-gdms/sys-article-or-draft/coverImg/" + sysUserId + "/" + date;
        return qiniuFeign.uploadOne(file, key);
    }

    /**
     * 删除文章封面图片
     * @param key
     * @return
     * @throws Exception
     */
    @DeleteMapping("/blog/write/article/cover/img")
    public BaseResult deleteArticleCoverImg(@RequestParam("key") String key) throws Exception {
        return qiniuFeign.deleteOne(key);
    }

    /**
     * 保存到草稿箱
     * @param authentication
     * @param articleVO
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "保存到草稿箱-controller")
    @PostMapping("/blog/write/draft")
    public BaseResult addDraft(@RequestBody ArticleVO articleVO,
                               Authentication authentication) throws Exception{
        articleVO.setSysUserId(securityOauth2Utils.getUserId(authentication));
        if (StringUtils.isBlank(articleVO.getTitle())) {
            articleVO.setTitle("自定义标题");
        }
        SysDraftEntity sysDraftEntity = SysDraftCover.INSTANCE.fromCoverEntity(articleVO);
        sysDraftEntity.setCreated(new Date());
        sysDraftEntity.setUpdated(new Date());

        if (sysDraftService.insert(sysDraftEntity) < 0) {
            return BaseResult.error(HttpConstant.SAVE_ERROR_MESSAGE);
        }
        return BaseResult.ok(HttpConstant.SAVE_MESSAGE);
    }

    /**
     * 编辑草稿箱
     * @param sysDraftId
     * @param articleVO
     * @return
     */
    @PrintlnLog(description = "编辑草稿箱-controller")
    @PutMapping("/blog/write/draft/{sysDraftId}")
    public BaseResult updateDraft(@PathVariable("sysDraftId") Long sysDraftId,
                                  @RequestBody ArticleVO articleVO,
                                  Authentication authentication) throws Exception{
        articleVO.setSysUserId(securityOauth2Utils.getUserId(authentication));
        if (StringUtils.isBlank(articleVO.getTitle())) {
            articleVO.setTitle("自定义标题");
        }
        SysDraftEntity sysDraftEntity = SysDraftCover.INSTANCE.fromCoverEntity(articleVO);
        sysDraftEntity.setSysDraftId(sysDraftId);
        sysDraftEntity.setUpdated(new Date());
        if (sysDraftService.update(sysDraftEntity) < 0) {
            return BaseResult.error(HttpConstant.ERROR_MESSAGE);
        }
        return BaseResult.ok(HttpConstant.SAVE_MESSAGE);
    }

    /**
     * 获取标签信息列表
     * @return
     */
    @PrintlnLog(description = "获取标签信息列表-controller")
    @GetMapping("/blog/write/article/label")
    public BaseResult getLabelInfo() {
        Map<String, Object> map = Maps.newHashMap();
        map.put(ResponseConstant.GET_LISTS, otherMapper.getLabelLists());
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

    /**
     * 搜索标签信息列表
     * @param keyWord
     * @return
     */
    @PrintlnLog(description = "搜索标签信息列表-controller")
    @GetMapping("/blog/write/article/label/query")
    public BaseResult queryLabelInfo(String keyWord) {
        Map<String, Object> map = Maps.newHashMap();
        map.put(ResponseConstant.GET_LISTS, otherMapper.queryLabelLists(keyWord));
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

    /**
     * 发布文章
     * @param articleVO
     * @param labelId
     * @param authentication
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "发布文章-controller")
    @PostMapping("/blog/write/article")
    public BaseResult faBuArticle(@RequestBody ArticleVO articleVO,
                                  Long labelId,
                                  Authentication authentication) throws Exception{
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        if (labelId == null || labelId < 0) {
            return BaseResult.error("请选择标签才能发布文章");
        }
        if (StringUtils.isBlank(articleVO.getTitle())) {
            return BaseResult.error("文章标题不能为空!");
        }
        if (StringUtils.isBlank(articleVO.getContent())) {
            return BaseResult.error("文章内容不能为空!");
        }
        SysArticleEntity sysArticleEntity = SysArticleCover.INSTANCE.fromCoverEntity(articleVO);
        sysArticleEntity.setSysUserId(sysUserId);
        sysArticleEntity.setLabelId(labelId);
        sysArticleEntity.setCreated(new Date());
        sysArticleEntity.setUpdated(new Date());
        Long articleId = sysArticleService.insertData(sysArticleEntity);
        if (articleId == null) {
            return BaseResult.error(HttpConstant.ERROR_MESSAGE);
        }
        return BaseResult.ok().put(HttpConstant.OK, "发布成功", "articleId", articleId);
    }

    /**
     * 获取草稿箱列表
     * @param authentication
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "获取草稿箱列表-controller")
    @GetMapping("/blog/write/draft/lists")
    public BaseResult getDraftLists(Authentication authentication) throws Exception{
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        Map<String, Object> map = Maps.newHashMap();
        List<SysDraftEntity> list = sysDraftMapper.getDraftLists(sysUserId);
        map.put(ResponseConstant.GET_LISTS, list);
        if (list != null && list.size() > 0) {
            map.put(ResponseConstant.COUNT, list.size());
        } else {
            map.put(ResponseConstant.COUNT, 0);
        }
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

    /**
     * 删除草稿箱
     * @param sysDraftId
     * @param authentication
     * @return
     */
    @PrintlnLog(description = "删除草稿箱-controller")
    @DeleteMapping("/blog/write/draft/{sysDraftId}")
    public BaseResult deleteDraft(@PathVariable("sysDraftId") Long sysDraftId,
                                  Authentication authentication) throws Exception{
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        SysDraftEntity sysDraftEntity = new SysDraftEntity();
        sysDraftEntity.setSysDraftId(sysDraftId);
        sysDraftEntity.setSysUserId(sysUserId);
        SysDraftEntity entity = sysDraftService.selectOneById(sysDraftEntity);
        if (StringUtils.isNotBlank(entity.getDraftImgKey())) {
            logger.debug("删除七牛云key " + entity.getDraftImgKey());
            qiniuFeign.deleteOne(entity.getDraftImgKey());
        }
        if (sysDraftService.deleteOneByKeyWord(sysDraftEntity) < 0) {
            return BaseResult.error(HttpConstant.ERROR_MESSAGE);
        }
        return BaseResult.ok(HttpConstant.DELETE_MESSAGE);
    }
}
