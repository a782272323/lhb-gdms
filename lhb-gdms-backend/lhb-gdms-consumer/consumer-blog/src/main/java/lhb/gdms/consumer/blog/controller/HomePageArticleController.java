package lhb.gdms.consumer.blog.controller;

import com.google.common.collect.Maps;
import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.constant.ResponseConstant;
import lhb.gdms.commons.domain.entity.*;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.configuration.aop.config.PrintlnLog;
import lhb.gdms.configuration.utils.SecurityOauth2Utils;
import lhb.gdms.consumer.blog.mapper.*;
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

/**
 * @author Herbie Leung(梁鸿斌)
 * @Description 我的主页-文章详情相关 controller
 * @date 2021/1/2
 * @time 12:56
 */
@RestController
@CrossOrigin
public class HomePageArticleController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SecurityOauth2Utils securityOauth2Utils;

    @Autowired
    private SysArticleMapper sysArticleMapper;

    @Autowired
    private SysArticlePraiseMapper sysArticlePraiseMapper;

    @Autowired
    private SysArticleCommentsMapper sysArticleCommentsMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysArticleService sysArticleService;

    @Autowired
    private SysArticlePraiseService sysArticlePraiseService;

    @Autowired
    private SysArticleCommentsService sysArticleCommentsService;

    @Autowired
    private OtherMapper otherMapper;

    @Autowired
    private OtherService otherService;

    @Autowired
    private SysCommentsReplyMapper sysCommentsReplyMapper;

    @Autowired
    private SysCommentsReplyService sysCommentsReplyService;

    /**
     * 文章被点击查看时被阅读数加
     *
     * @param articleId
     * @return
     */
    @PrintlnLog(description = "文章被点击查看时被阅读数加一-controller")
    @PostMapping("/web/blog/home-page-article/article/browse/{articleId}")
    public BaseResult insertArticleBrowse(@PathVariable("articleId") Long articleId) {
        if (sysArticleMapper.insertArticleBrowse(articleId) <= 0) {
            return BaseResult.error(HttpConstant.ERROR_MESSAGE);
        }
        return BaseResult.ok();
    }

    /**
     * 文章以及文章有关详情
     *
     * @param articleId
     * @return
     */
    @PrintlnLog(description = "文章以及文章有关详情-controller")
    @GetMapping("/web/blog/home-page-article/article/details/{articleId}")
    public BaseResult getArticleDetails(@PathVariable("articleId") Long articleId) {
        Map<String, Object> map = Maps.newHashMap();
        List<SysArticleEntity> articleEntities = sysArticleMapper.getArticleDetailsByArticleId(articleId);
        // 文章详情
        map.put("articleDetails", articleEntities);
        // 文章评论数量
        map.put("articleCommentsCount", otherService.getArticleCommentsCountAll(articleId));
        // 文章点赞数量
        map.put("articlePraiseCount", sysArticlePraiseMapper.getArticlePraiseCount(articleId));
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

    /**
     * 获取文章相关用户详情标题
     *
     * @param articleId
     * @return
     */
    @PrintlnLog(description = "获取文章相关用户详情标题-controller")
    @GetMapping("/web/blog/home-page-article/user/details/{articleId}/{sysUserId}")
    public BaseResult getArticleUserDetails(@PathVariable("articleId") Long articleId,
                                            @PathVariable("sysUserId") Long sysUserId) {
        SysArticleEntity sysArticleEntity = sysArticleMapper.getArticleInfoByArticleId(articleId);
        Long userId = sysArticleEntity.getSysUserId();
        Map<String, Object> map = Maps.newHashMap();
        map.put("userDetails", sysUserMapper.getUserInfoByUserId(userId));
        map.put("userPriseAll", sysArticleService.getArticlePriseAll(userId));
        map.put("userBrowseAll", sysArticleService.getArticleBrowseAll(userId));
        List<Map<String, Object>> list = sysArticleMapper.getArticleBySysUserId(userId);
        list.stream().forEach(item -> {
            Long id = Long.parseLong(item.get("articleId").toString());
            item.put("articleDetails", sysArticleMapper.getArticlesDetailsToCollection(id).get(0));
            // 获取文章点赞数量
            item.put("articlePraiseCount", sysArticlePraiseService.getArticlePraiseCount(id));
            // 获取文章评论数量
            item.put("articleCommentsCount", otherService.getArticleCommentsCountAll(id));
            // 判断是否当前用户点赞
            Long i = Long.valueOf(-1);
            if (sysUserId.longValue() != i.longValue()) {
                item.put("isPraise", sysArticlePraiseService.findInfoById(id, sysUserId));
            } else {
                item.put("isPraise", false);
            }
        });
        map.put("userArticleDetails", list.stream().limit(5));
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

    /**
     * 判断当前文章是否是当前用户点赞过了
     *
     * @param articleId
     * @param authentication
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "判断当前文章是否是当前用户点赞过了-controller")
    @GetMapping("/blog/home-page-article/user/isPraise/{articleId}")
    public BaseResult isUserPraise(@PathVariable("articleId") Long articleId,
                                   Authentication authentication) throws Exception {
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        Map<String, Object> map = Maps.newHashMap();
        map.put("isPraise", sysArticlePraiseService.findInfoById(articleId, sysUserId));
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

    /**
     * 点赞文章
     * @param articleId
     * @param authentication
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "点赞文章-controller")
    @PostMapping("/blog/home-page-article/article/praise/{articleId}")
    public BaseResult addArticlePraise(@PathVariable("articleId") Long articleId,
                                       Authentication authentication) throws Exception{
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        SysArticlePraiseEntity sysArticlePraiseEntity = new SysArticlePraiseEntity();
        sysArticlePraiseEntity.setArticleId(articleId);
        sysArticlePraiseEntity.setSysUserId(sysUserId);
        sysArticlePraiseEntity.setCreated(new Date());
        sysArticlePraiseEntity.setUpdated(new Date());
        SysArticlePraiseEntity entity = sysArticlePraiseMapper.findInfoById(articleId, sysUserId);
        if (entity != null) {
            return BaseResult.ok("您已经赞过该文章啦!");
        }
        if (sysArticlePraiseService.insert(sysArticlePraiseEntity) <= 0) {
            return BaseResult.error(HttpConstant.ERROR_MESSAGE);
        }
        return BaseResult.ok("点赞成功!");
    }

    /**
     * 取消点赞
     * @param articleId
     * @param authentication
     * @return
     */
    @PrintlnLog(description = "取消点赞-controller")
    @DeleteMapping("/blog/home-page-article/article/praise/{articleId}")
    public BaseResult deleteArticlePraise(@PathVariable("articleId") Long articleId,
                                          Authentication authentication) throws Exception{
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        SysArticlePraiseEntity sysArticlePraiseEntity = new SysArticlePraiseEntity();
        sysArticlePraiseEntity.setSysUserId(sysUserId);
        sysArticlePraiseEntity.setArticleId(articleId);
        if (sysArticlePraiseService.deleteOneByKeyWord(sysArticlePraiseEntity) <= 0) {
            return BaseResult.error(HttpConstant.ERROR_MESSAGE);
        }
        return BaseResult.ok("取消成功!");
    }

    /**
     * 根据文章id获取文章评论表全部列表信息
     * @param articleId
     * @return
     */
    @PrintlnLog(description = "根据文章id获取文章评论表全部列表信息-controller")
    @GetMapping("/web/blog/home-page-article/comments/lists/{articleId}/{sysUserId}")
    public BaseResult getCommentsLists(@PathVariable("articleId") Long articleId,
                                       @PathVariable("sysUserId") Long sysUserId) {
        List<Map<String, Object>> list = sysArticleCommentsMapper.getCommentsLists(articleId);
        list.stream().forEach(item -> {
            Long userId = Long.parseLong(item.get("sysUserId").toString());
            SysArticleEntity sysArticleEntity = sysArticleMapper.getArticleInfoByArticleId(articleId);
            Long sysArticleCommentsId = Long.parseLong(item.get("sysArticleCommentsId").toString());
            // 判断评论是不是文章作者发的
            if (userId.equals(sysArticleEntity.getSysUserId())) {
                item.put("isArticleAuthor", true);
            } else {
                item.put("isArticleAuthor", false);
            }
            // 获取评论点赞数量
            item.put("articleCommentsPraiseCount", otherMapper.getCommentsPraiseCount(sysArticleCommentsId));
            // 判断评论是否是当前登录用户点赞
            Long i = Long.valueOf(-1);
            if (sysUserId.longValue() != i.longValue()) {
                item.put("isArticleCommentsPraise", otherService.checkCommentsPraiseByTwoId(sysArticleCommentsId, sysUserId));
            } else {
                item.put("isArticleCommentsPraise", false);
            }
            // 回复详情
            List<Map<String, Object>> replyLists = sysCommentsReplyMapper.getCommentsReplyLists(sysArticleCommentsId);
            item.put("commentsReplyLists", replyLists);
            replyLists.stream().forEach(row -> {
                String reply = "reply";
                String comment = "comment";
                Long l = Long.valueOf(-1);

                Long sysCommentsReplyId = Long.parseLong(row.get("sysCommentsReplyId").toString());
                Long fromSysUserId = Long.parseLong(row.get("fromSysUserId").toString());
                String commentsReplyType = row.get("commentsReplyType").toString();
                SysUserEntity sysUserEntity = sysUserMapper.getUserInfoByUserId(fromSysUserId);
                Integer replyPraiseCount = otherMapper.getReplyPraiseCount(sysCommentsReplyId);

                // 获取回复点赞数量
                row.put("replyPraiseCount", replyPraiseCount);
                // 若回复类型为reply，则查询目标用户的昵称,若回复类型为comment，则目标用户为空
                if (reply.equals(commentsReplyType)) {
                    Long toSysUserId = Long.parseLong(row.get("toSysUserId").toString());
                    row.put("toSysUserNickname", sysUserMapper.getUserInfoByUserId(toSysUserId).getSysUserNickname());
                }
                if (comment.equals(commentsReplyType)) {
                    row.put("toSysUserNickname", null);
                }
                // 查询回复用户昵称
                row.put("fromSysUserNickname", sysUserEntity.getSysUserNickname());
                // 判断该条回复是否是当前登录用户点赞
                if (replyPraiseCount > 0) {
                    row.put("isPraise", otherService.checkReplyPraiseByTwoId(sysCommentsReplyId, sysUserId));
                } else {
                    row.put("isPraise", false);
                }
                // 回复用户头像
                row.put("fromSysUserIcon", sysUserEntity.getSysUserIcon());
            });
        });
        Map<String, Object> map = Maps.newHashMap();
        map.put("commentsLists", list);
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

    /**
     * 发表评论
     * @param articleId
     * @param articleCommentsContent
     * @param authentication
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "发表评论-controller")
    @PostMapping("/blog/home-page-article/comments/{articleId}")
    public BaseResult insertArticlesComments(@PathVariable("articleId") Long articleId,
                                     String articleCommentsContent,
                                     Authentication authentication) throws Exception {
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        SysArticleCommentsEntity entity = new SysArticleCommentsEntity();
        entity.setArticleCommentsContent(articleCommentsContent);
        entity.setArticleId(articleId);
        entity.setSysUserId(sysUserId);
        entity.setUpdated(new Date());
        entity.setCreated(new Date());
        return sysArticleCommentsService.insert(entity) <= 0
                ? BaseResult.error(HttpConstant.ERROR_MESSAGE)
                : BaseResult.ok("评论成功!");
    }

    /**
     * 点赞评论
     * @param sysArticleCommentsId
     * @param authentication
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "点赞评论-controller")
    @PostMapping("/blog/home-page-article/comments/praise/{sysArticleCommentsId}")
    public BaseResult insertCommentsPraise(@PathVariable("sysArticleCommentsId") Long sysArticleCommentsId,
                                           Authentication authentication) throws Exception {
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        SysCommentsPraiseEntity entity = new SysCommentsPraiseEntity();
        entity.setSysUserId(sysUserId);
        entity.setSysArticleCommentsId(sysArticleCommentsId);
        entity.setCreated(new Date());
        entity.setUpdated(new Date());
        return otherMapper.insertCommentsPraise(entity) <= 0
               ? BaseResult.error(HttpConstant.ERROR_MESSAGE)
               : BaseResult.ok("点赞成功!");
    }

    /**
     * 取消评论点赞
     * @param sysArticleCommentsId
     * @param authentication
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "取消评论点赞-controller")
    @DeleteMapping("/blog/home-page-article/comments/praise/{sysArticleCommentsId}")
    public BaseResult deleteCommentsPraise(@PathVariable("sysArticleCommentsId") Long sysArticleCommentsId,
                                           Authentication authentication) throws Exception {
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        return otherMapper.deleteCommentsPraise(sysArticleCommentsId, sysUserId) <= 0
                ? BaseResult.error(HttpConstant.ERROR_MESSAGE)
                : BaseResult.ok("取消成功");
    }

    /**
     * 点赞回复
     * @param sysCommentsReplyId
     * @param authentication
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "点赞回复-controller")
    @PostMapping("/blog/home-page-article/reply/praise/{sysCommentsReplyId}")
    public BaseResult insertReplyPraise(@PathVariable("sysCommentsReplyId") Long sysCommentsReplyId,
                                        Authentication authentication) throws Exception {
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        return otherService.insertReplyPraise(sysCommentsReplyId, sysUserId) <= 0
                ? BaseResult.error(HttpConstant.ERROR_MESSAGE)
                : BaseResult.ok("点赞成功!");
    }

    /**
     * 取消回复的点赞
     * @param sysCommentsReplyId
     * @param authentication
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "取消回复的点赞-controller")
    @DeleteMapping("/blog/home-page-article/reply/praise/{sysCommentsReplyId}")
    public BaseResult deleteReplyPraise(@PathVariable("sysCommentsReplyId") Long sysCommentsReplyId,
                                        Authentication authentication) throws Exception {
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        return otherService.deleteReplyPraise(sysCommentsReplyId, sysUserId) <= 0
                ? BaseResult.error(HttpConstant.ERROR_MESSAGE)
                : BaseResult.ok("取消成功!");
    }

    /**
     * 回复评论
     * @param entity
     * @param authentication
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "回复评论-controller")
    @PostMapping("/blog/home-page-article/comments/reply")
    public BaseResult insertCommentsOrReply(@RequestBody SysCommentsReplyEntity entity,
                                            Authentication authentication) throws Exception{
        Long fromSysUserId = securityOauth2Utils.getUserId(authentication);
        entity.setFromSysUserId(fromSysUserId);
        entity.setCreated(new Date());
        entity.setUpdated(new Date());
        if (StringUtils.isBlank(entity.getCommentsReplyContent())) {
            return BaseResult.error("评论内容不能为空!");
        }
        return sysCommentsReplyService.insert(entity) <= 0
                ? BaseResult.error(HttpConstant.ERROR_MESSAGE)
                : BaseResult.ok("评论成功!");
    }

}
