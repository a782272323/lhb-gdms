package lhb.gdms.consumer.blog.controller;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.constant.ResponseConstant;
import lhb.gdms.commons.domain.entity.SysInputHistoryEntity;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.commons.utils.RedisUtils;
import lhb.gdms.configuration.aop.config.PrintlnLog;
import lhb.gdms.configuration.utils.SecurityOauth2Utils;
import lhb.gdms.consumer.blog.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description  搜索 controller
 * @author Herbie Leung(梁鸿斌)
 * @date 2021/1/22
 * @time 18:32
 */
@RestController
@CrossOrigin
public class HomeSearchController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SecurityOauth2Utils securityOauth2Utils;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private SysInputHistoryService sysInputHistoryService;

    @Autowired
    private SysArticleService sysArticleService;

    @Autowired
    private SysArticlePraiseService sysArticlePraiseService;

    @Autowired
    private OtherService otherService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysLabelService sysLabelService;

    @Autowired
    private SysUserFocusService sysUserFocusService;

    /**
     * 新增搜索框历史记录
     * @param searchContent
     * @param authentication
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "新增搜索框历史记录-controller")
    @PostMapping("/blog/home-search/search")
    public BaseResult insertInputHistory(String searchContent, Authentication authentication) throws Exception {
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        int number = 5;

        SysInputHistoryEntity entity = new SysInputHistoryEntity();
        entity.setSysInputHistoryContent(searchContent);
        entity.setSysUserId(sysUserId);
        entity.setCreated(new Date());
        entity.setUpdated(new Date());

        SysInputHistoryEntity sysInputHistoryEntity = sysInputHistoryService.selectOneByKeyWord(entity);
        if (sysInputHistoryEntity != null) {
            // 若搜索内容重复先删除重复的再新增
            if (searchContent.equals(sysInputHistoryEntity.getSysInputHistoryContent())) {
                sysInputHistoryService.deleteOneById(sysInputHistoryEntity.getSysInputHistoryId());
            }
        }
        if (sysInputHistoryEntity == null) {
            // 若搜索数据大于5条则删除id最小的那条数据再新增
            int count = sysInputHistoryService.getInputHistoryLists(entity);
            if (count >= number) {
                logger.error("" + count);
                sysInputHistoryService.deleteOneById(sysInputHistoryService.getMinIdBySysUserId(sysUserId));
            }
        }

        return sysInputHistoryService.insert(entity) > 0
                ? BaseResult.ok()
                : BaseResult.error(HttpConstant.ERROR_MESSAGE);
    }

    /**
     * 获取搜索框历史记录
     * @param searchContent
     * @param authentication
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "获取搜索框历史记录-controller")
    @GetMapping("/blog/home-search/search")
    public BaseResult getInputHistory(String searchContent, Authentication authentication) throws Exception {
        Long sysUserId = securityOauth2Utils.getUserId(authentication);

        Map<String, Object> map = Maps.newHashMap();
        map.put("inputHistoryLists", sysInputHistoryService.getInputHistoryAll(sysUserId));
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

    /**
     * 修改搜索框历史记录顺序
     * @param searchContent
     * @param authentication
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "修改搜索框历史记录顺序-controller")
    @PutMapping("/blog/home-search/search")
    public BaseResult updateInputHistory(String searchContent, Authentication authentication) throws Exception {
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        SysInputHistoryEntity entity = new SysInputHistoryEntity();
        entity.setUpdated(new Date());
        entity.setSysInputHistoryContent(searchContent);
        entity.setSysUserId(sysUserId);
        return sysInputHistoryService.update(entity) > 0
                ? BaseResult.ok()
                : BaseResult.error(HttpConstant.ERROR_MESSAGE);
    }

    /**
     * 删除搜索框历史记录
     * @param sysInputHistoryId
     * @return
     */
    @PrintlnLog(description = "删除搜索框历史记录-controller")
    @DeleteMapping("/blog/home-search/search/{sysInputHistoryId}")
    public BaseResult deleteInputHistory(@PathVariable("sysInputHistoryId") Long sysInputHistoryId) {
        return sysInputHistoryService.deleteOneById(sysInputHistoryId) > 0
                ? BaseResult.ok("历史记录删除成功!")
                : BaseResult.error(HttpConstant.ERROR_MESSAGE);
    }

    /**
     * 清空搜索框历史记录
     * @param authentication
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "清空搜索框历史记录-controller")
    @DeleteMapping("/blog/home-search/search/clear")
    public BaseResult clearInputHistory(Authentication authentication) throws Exception {
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        return sysInputHistoryService.clearInputHistory(sysUserId) > 0
                ? BaseResult.ok("历史记录成功清空!")
                : BaseResult.error(HttpConstant.ERROR_MESSAGE);
    }

    /**
     * 搜索文章
     * @param articleType all days weeks months
     * @param search
     * @return
     */
    @PrintlnLog(description = "搜索文章-controller")
    @GetMapping("/web/blog/home-search/search/article/{sysUserId}")
    public BaseResult searchArticleLists(@PathVariable("sysUserId") Long sysUserId,
                                         String articleType, String search) {
        Map<String, Object> map = Maps.newHashMap();
        List<Map<String, Object>> list = sysArticleService.searchArticleLists(search, articleType);
        Long i = Long.valueOf(-1);

        if (StringUtils.isBlank(search)) {
            return BaseResult.error("搜索内容不能为空!");
        }

        list.stream().forEach(item -> {
            Long articleId = Long.parseLong(item.get("articleId").toString());
            // 文章内容提取为纯文本处理
            item.put("articleContent", sysArticleService.searchArticleContent(articleId));
            // 文章点赞数量
            item.put("articlePraiseCount", sysArticlePraiseService.getArticlePraiseCount(articleId));
            // 文章评论数量
            item.put("articleCommentsCount", otherService.getArticleCommentsCountAll(articleId));
            // 判断是否当前登录用户点赞
            if (sysUserId.longValue() != i.longValue()) {
                item.put("isPraise", sysArticlePraiseService.findInfoById(articleId, sysUserId));
            } else {
                item.put("isPraise", false);
            }
        });
        map.put("articleLists", list);
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

    /**
     * 搜索用户
     * @param search
     * @return
     */
    @PrintlnLog(description = "搜索用户-controller")
    @GetMapping("/web/blog/home-search/search/user/{sysUserId}")
    public BaseResult searchUserLists(@PathVariable("sysUserId") Long sysUserId,
                                      String search) {
        Map<String, Object> map = Maps.newHashMap();
        List<Map<String, Object>> list = sysUserService.searchUserLists(search);
        Long i = Long.valueOf(-1);

        list.stream().forEach(item -> {
            Long userId = Long.valueOf(item.get("sysUserId").toString());
            if (sysUserId.longValue() != i.longValue()) {
                // 当前登录用户是否关注了该用户
                boolean isFocus = sysUserFocusService.checkFocusByTwoId(userId, sysUserId);
                item.put("isFocus", isFocus);
            } else {
                item.put("isFocus", false);
            }
        });
        map.put("userLists", list);
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

    /**
     * 搜索标签
     * @param search
     * @return
     */
    @PrintlnLog(description = "搜索标签-controller")
    @GetMapping("/web/blog/home-search/search/label/{sysUserId}")
    public BaseResult searchLabelLists(@PathVariable("sysUserId") Long sysUserId,
                                       String search) {
        Map<String, Object> map = Maps.newHashMap();
        List<Map<String, Object>> list = sysLabelService.searchLabelLists(search);
        Long i = Long.valueOf(-1);

        list.stream().forEach(item -> {
            Long labelId = Long.valueOf(item.get("labelId").toString());
            // 关注人数
            item.put("labelFocusCount", otherService.getLabelFocusCount(labelId));
            // 判断当前登录用户是否关注了该标签
            if (i.longValue() != sysUserId.longValue()) {
                item.put("isFocus", otherService.findLabelFocusInfoById(sysUserId, labelId));
            } else {
                item.put("isFocus", false);
            }
            // 文章数
            item.put("labelArticleCount", sysArticleService.getLabelArticleCount(labelId));
        });
        map.put("labelLists", list);
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

}
