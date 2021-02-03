package lhb.gdms.consumer.blog.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.constant.ResponseConstant;
import lhb.gdms.commons.domain.entity.SysUserFocusEntity;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.configuration.aop.config.PrintlnLog;
import lhb.gdms.configuration.utils.SecurityOauth2Utils;
import lhb.gdms.consumer.blog.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Description  我的主页-用户主页
 * @author Herbie Leung(梁鸿斌)
 * @date 2021/1/19
 * @time 11:29
 */
@RestController
@CrossOrigin
public class HomePageUserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SecurityOauth2Utils securityOauth2Utils;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysArticleService sysArticleService;

    @Autowired
    private SysArticleCommentsService sysArticleCommentsService;

    @Autowired
    private SysCommentsReplyService sysCommentsReplyService;

    @Autowired
    private SysUserFocusService sysUserFocusService;

    @Autowired
    private SysLabelService sysLabelService;

    @Autowired
    private SysCollectionService sysCollectionService;

    @Autowired
    private SysArticlePraiseService sysArticlePraiseService;

    @Autowired
    private OtherService otherService;

    /**
     * 获取主页用户相关数据列表详情
     * @param sysUserId
     * @return
     */
    @PrintlnLog(description = "获取用户主页数据列表详情-controller")
    @GetMapping("/web/blog/home-page-user/{sysUserId}")
    public BaseResult getHomePageUserLists(@PathVariable("sysUserId") Long sysUserId) {

        List<Map<String, Object>> list = sysUserService.getHomeUserInfoById(sysUserId);
        list.stream().forEach(item -> {
            // 获取用户发表文章总数
            item.put("userArticleCountAll", sysArticleService.getHomeUserArticleCount(sysUserId));
            // 获取用户收获的点赞总数
            item.put("userArticlePraiseCountAll", sysArticlePraiseService.getUserArticlePraise(sysUserId));
            // 获取用户发表文章被阅读总数
            item.put("userArticleBrowseCountAll", sysArticleService.getArticleBrowseAll(sysUserId));
            // 获取用户发表的评论和回复总数
            Integer articleCommentsCountAll = sysArticleCommentsService.getHomeUserCommentsCountAll(sysUserId);
            Integer articleReplyCountAll = sysCommentsReplyService.getHomeUserReplyCountAll(sysUserId);
            item.put("userCommentsCountAll", articleCommentsCountAll + articleReplyCountAll);
            // 获取用户关注的人总数
            item.put("userFocusPersonCountAll", sysUserFocusService.getHomeUserFocusPersonCountAll(sysUserId));
            // 获取用户的粉丝总数
            item.put("userFocusFansCountAll", sysUserFocusService.getHomeUserFocusFansCountAll(sysUserId));
            // 获取用户关注的标签总数
            item.put("userFocusLabelCountAll", sysLabelService.getHomeUserFocusLabelCountAll(sysUserId));
            // 获取用户创建的收藏集总数
            item.put("userCollectionCountAll", sysCollectionService.getHomeUserCollectionCountAll(sysUserId));
        });

        Map<String, Object> map = Maps.newHashMap();
        map.put("homeUserLists", list);
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

    /**
     * 当前登录用户是否关注了某一位用户
     * @param formSysUserId
     * @param authentication
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "当前登录用户是否关注了某一位用户-controller")
    @GetMapping("/blog/home-page-user/focus/user/{formSysUserId}")
    public BaseResult checkFocusUser(@PathVariable("formSysUserId") Long formSysUserId,
                                     Authentication authentication) throws Exception {
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        SysUserFocusEntity sysUserFocusEntity = sysUserFocusService.getFocusInfoByTwoId(formSysUserId, sysUserId);
        if (sysUserFocusEntity != null) {
            return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, "isFocus", "true");
        } else {
            return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, "isFocus", "false");
        }
    }

    /**
     * 用户主页详情
     * @param sysUserId 用户主页id
     * @param userId 当前登录用户id
     * @param blogType
     * @return
     */
    @PrintlnLog(description = "用户主页详情-controller")
    @GetMapping("/web/blog/home-page-user/details/lists/{sysUserId}/{userId}")
    public BaseResult getHomeUserDetails(@PathVariable("sysUserId") Long sysUserId,
                                         @PathVariable("userId") Long userId,
                                         String blogType) {
        String article = "article";
        String dynamic = "dynamic";
        String comment = "comment";
        String praise = "praise";
        Long i = Long.valueOf(-1);

        Map<String, Object> map = Maps.newHashMap();
        List<Map<String, Object>> list = Lists.newArrayList();
        // 文章属性
        if (blogType.equals(article)) {
            list = sysArticleService.getArticleBySysUserId(sysUserId);
            list.stream().forEach(item -> {
                Long articleId = Long.parseLong(item.get("articleId").toString());
                // 文章点赞数量
                item.put("articlePraiseCount", sysArticlePraiseService.getArticlePraiseCount(articleId));
                // 文章评论总数
                item.put("articleCommentsCount", otherService.getArticleCommentsCountAll(articleId));
                // 判断该文章是否是当前登录用户点赞
                if (userId.longValue() != i.longValue()) {
                    item.put("isPraise", sysArticlePraiseService.findInfoById(articleId, userId));
                } else {
                    item.put("isPraise", false);
                }
            });
            map.put("articleLists", list);
        }
        // 动态属性
        if (blogType.equals(dynamic)) {
            list = sysUserService.getHomeDynamicInfo(userId);
            map.put("dynamicLists", list);
        }
        // 评论属性
        if (blogType.equals(comment)) {
            list = sysArticleService.getHomeCommentsLists(userId);
            map.put("commentsLists", list);
        }
        // 点赞属性
        if (blogType.equals(praise)) {
            list = otherService.getHomePraiseLists(userId);
            map.put("praiseLists", list);
        }

        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }


    /**
     * 用户主页取消对用户的关注
     * @param formSysUserId
     * @param authentication
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "用户主页取消对用户的关注-controller")
    @DeleteMapping("/blog/home-page-user/focus/person/{formSysUserId}")
    public BaseResult deleteHomeFocusPerson(@PathVariable("formSysUserId") Long formSysUserId,
                                            Authentication authentication) throws Exception{
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        SysUserFocusEntity entity = new SysUserFocusEntity();
        entity.setSysUserId(sysUserId);
        entity.setFormSysUserId(formSysUserId);
        return sysUserFocusService.deleteOneByKeyWord(entity) > 0
                ? BaseResult.ok("取消成功")
                : BaseResult.error(HttpConstant.ERROR_MESSAGE);
    }
}
