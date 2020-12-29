package lhb.gdms.consumer.blog.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.constant.ResponseConstant;
import lhb.gdms.commons.domain.entity.SysCollectionEntity;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.configuration.aop.config.PrintlnLog;
import lhb.gdms.configuration.utils.SecurityOauth2Utils;
import lhb.gdms.consumer.blog.mapper.SysCollectionMapper;
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
        map.put(ResponseConstant.GET_LISTS, collectionEntities);
        if (collectionEntities != null && collectionEntities.size() > 0) {
            map.put(ResponseConstant.COUNT, collectionEntities.size());
        } else {
            map.put(ResponseConstant.COUNT, 0);
        }

        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

}
