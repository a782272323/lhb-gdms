package lhb.gdms.consumer.blog.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.constant.ResponseConstant;
import lhb.gdms.commons.domain.entity.SysArticleCollectionEntity;
import lhb.gdms.commons.domain.entity.SysArticleEntity;
import lhb.gdms.commons.domain.entity.SysCollectionEntity;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.configuration.aop.config.PrintlnLog;
import lhb.gdms.configuration.utils.SecurityOauth2Utils;
import lhb.gdms.consumer.blog.mapper.*;
import lhb.gdms.consumer.blog.service.SysArticleCommentsService;
import lhb.gdms.consumer.blog.service.SysArticlePraiseService;
import lhb.gdms.consumer.blog.service.SysCollectionService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description  收藏集 controller
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/27
 * @time 17:24
 */
@RestController
@CrossOrigin
public class CollectionController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SecurityOauth2Utils securityOauth2Utils;

    @Autowired
    private SysCollectionService sysCollectionService;

    @Autowired
    private SysCollectionMapper sysCollectionMapper;

    @Autowired
    private SysArticleCollectionMapper sysArticleCollectionMapper;

    @Autowired
    private SysArticleMapper sysArticleMapper;

    @Autowired
    private SysArticlePraiseMapper sysArticlePraiseMapper;

    @Autowired
    private SysArticleCommentsMapper sysArticleCommentsMapper;

    @Autowired
    private SysArticlePraiseService sysArticlePraiseService;

    @Autowired
    private SysArticleCommentsService sysArticleCommentsService;

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 新建收藏集
     * @param sysCollectionName
     * @param authentication
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "新建收藏集-controller")
    @PostMapping("/blog/collection")
    public BaseResult insertCollection(String sysCollectionName,
                                       Authentication authentication) throws Exception{
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        if (StringUtils.isBlank(sysCollectionName)) {
            return BaseResult.error("收藏集名称不能为空");
        }
        if (sysCollectionService.checkNameByIdToInsert(sysUserId, sysCollectionName) == true) {
            return BaseResult.error("收藏集合名称已存在");
        }
        SysCollectionEntity sysCollectionEntity = new SysCollectionEntity();
        sysCollectionEntity.setSysUserId(sysUserId);
        sysCollectionEntity.setSysCollectionName(sysCollectionName);
        sysCollectionEntity.setCreated(new Date());
        sysCollectionEntity.setUpdated(new Date());
        if (sysCollectionService.insert(sysCollectionEntity) <= 0) {
            return BaseResult.error(HttpConstant.ERROR_MESSAGE);
        }
        return BaseResult.ok(HttpConstant.SAVE_MESSAGE);
    }

    /**
     * 编辑收藏集名称
     * @param sysCollectionId
     * @param sysCollectionName
     * @param authentication
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "编辑收藏集名称-controller")
    @PutMapping("/blog/collection/{sysCollectionId}")
    public BaseResult updateCollectionName(@PathVariable("sysCollectionId") Long sysCollectionId,
                                           String sysCollectionName,
                                           Authentication authentication) throws Exception{
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        if (StringUtils.isBlank(sysCollectionName)) {
            return BaseResult.error("收藏集名称不能为空");
        }
        if (sysCollectionService.checkNameByIdToUpdate(sysCollectionId, sysCollectionName, sysUserId) == true) {
            return BaseResult.error("收藏集合名称已存在");
        }
        SysCollectionEntity sysCollectionEntity = new SysCollectionEntity();
        sysCollectionEntity.setSysCollectionId(sysCollectionId);
        sysCollectionEntity.setSysCollectionName(sysCollectionName);
        sysCollectionEntity.setSysUserId(sysUserId);
        sysCollectionEntity.setCreated(new Date());
        sysCollectionEntity.setUpdated(new Date());
        if (sysCollectionService.update(sysCollectionEntity) <= 0) {
            return BaseResult.error(HttpConstant.ERROR_MESSAGE);
        }
        return BaseResult.ok(HttpConstant.EDIT_MESSAGE);
    }

    /**
     * 删除收藏集
     * @param sysCollectionId
     * @param authentication
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "删除收藏集-controller")
    @DeleteMapping("/blog/collection/{sysCollectionId}")
    public BaseResult deleteOne(@PathVariable("sysCollectionId") Long sysCollectionId,
                                Authentication authentication) throws Exception{
        SysCollectionEntity sysCollectionEntity = new SysCollectionEntity();
        sysCollectionEntity.setSysCollectionId(sysCollectionId);
        sysCollectionEntity.setSysUserId(securityOauth2Utils.getUserId(authentication));
        List<Map<String, Object>> list = sysArticleCollectionMapper.getListsById(sysCollectionId);
        if (list.size() > 0 && list != null) {
            logger.debug("删除关系表数据");
            list.stream().forEach(item -> {
                Long articleId = Long.parseLong(item.get("articleId").toString());
                sysArticleCollectionMapper.deleteOne(articleId, sysCollectionId);
            });                                                                   
        }
        if (sysCollectionMapper.deleteOneByKeyWord(sysCollectionEntity) < 0) {
            return BaseResult.error(HttpConstant.ERROR_MESSAGE);
        }
        return BaseResult.ok(HttpConstant.DELETE_MESSAGE);
    }

    /**
     * 获取收藏集合列表信息
     * @param authentication
     * @return
     */
    @PrintlnLog(description = "获取收藏集合列表信息-controller")
    @GetMapping("/blog/collection/lists")
    public BaseResult getCollectionLists(Authentication authentication) throws Exception{
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        Map<String, Object> map = Maps.newHashMap();
        List<SysCollectionEntity> collectionEntities = sysCollectionMapper.getCollectionLists(sysUserId);
        List<Map<String, Object>> list = Lists.newArrayList();
        list.add(map);
        map.put(ResponseConstant.GET_LISTS, collectionEntities);
        if (collectionEntities != null && collectionEntities.size() > 0) {
            map.put(ResponseConstant.COUNT, collectionEntities.size());
        } else {
            map.put(ResponseConstant.COUNT, 0);
        }

        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

    /**
     * 查询收藏集下文章详情
     * @param sysCollectionId
     * @param authentication
     * @return
     */
    @PrintlnLog(description = "查询收藏集下文章详情-controller")
    @GetMapping("/blog/collection/list/{sysCollectionId}")
    public BaseResult getCollectionListById(@PathVariable("sysCollectionId") Long sysCollectionId,
                                            Authentication authentication) throws Exception{
        Long sysUserId = securityOauth2Utils.getUserId(authentication);

        List<Map<String, Object>> list = sysArticleCollectionMapper.getListsById(sysCollectionId);
        list.stream().forEach(item -> {
            Long articleId =Long.parseLong(item.get("articleId").toString());
            item.put("articleDetails", sysArticleMapper.getArticlesDetailsToCollection(articleId).get(0));
            item.put("articlePraiseCount", sysArticlePraiseService.getArticlePraiseCount(articleId));
            item.put("articleCommentsCount", sysArticleCommentsMapper.getArticleCommentsCount(articleId));
            item.put("userNickName", sysUserMapper.findNicknameById(sysUserId));
            item.put("isPraise", sysArticlePraiseService.findInfoById(articleId, sysUserId));
        });
        Map<String, Object> map = Maps.newHashMap();
        map.put(ResponseConstant.GET_LIST, sysCollectionMapper.getCollectionListById(sysUserId, sysCollectionId));
        map.put("articleLists", list);
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }


}
