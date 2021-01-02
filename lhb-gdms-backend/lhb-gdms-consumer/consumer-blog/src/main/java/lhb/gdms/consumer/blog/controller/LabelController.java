package lhb.gdms.consumer.blog.controller;

import com.google.common.collect.Maps;
import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.constant.ResponseConstant;
import lhb.gdms.commons.domain.entity.SysLabelFocusEntity;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.configuration.aop.config.PrintlnLog;
import lhb.gdms.configuration.utils.SecurityOauth2Utils;
import lhb.gdms.consumer.blog.mapper.*;
import lhb.gdms.consumer.blog.service.OtherService;
import lhb.gdms.consumer.blog.service.SysArticlePraiseService;
import lhb.gdms.consumer.blog.service.SysArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author Herbie Leung(梁鸿斌)
 * @Description 标签管理 controller
 * @date 2020/12/31
 * @time 18:37
 */
@RestController
@CrossOrigin
public class LabelController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SecurityOauth2Utils securityOauth2Utils;

    @Autowired
    private SysLabelMapper sysLabelMapper;

    @Autowired
    private OtherMapper otherMapper;

    @Autowired
    private OtherService otherService;

    @Autowired
    private SysArticleMapper sysArticleMapper;

    @Autowired
    private SysArticleService sysArticleService;

    @Autowired
    private SysArticlePraiseService sysArticlePraiseService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysArticleCommentsMapper sysArticleCommentsMapper;

    /**
     * 获取所有标签详细信息包括查询
     *
     * @param sortType       排序方式 最热-hottest，最新-latest
     * @param authentication
     * @param keyWord
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "获取所有标签详细信息-controller")
    @GetMapping("/blog/label/all/lists")
    public BaseResult getAllLabelLists(String sortType,
                                       String keyWord,
                                       Authentication authentication) throws Exception {
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        Map<String, Object> map = Maps.newHashMap();
        List<Map<String, Object>> list = sysLabelMapper.selectAllLabelLists(keyWord);
        list.stream().forEach(item -> {
            Long labelId = Long.parseLong(item.get("labelId").toString());
            // 关注人数
            item.put("labelFocusCount", otherService.getLabelFocusCount(labelId));
            // 判断当前登录用户是否关注了该标签
            if (otherService.findLabelFocusInfoById(sysUserId, labelId) == true) {
                item.put("isFocus", true);
            } else {
                item.put("isFocus", false);
            }
            // 文章数
            item.put("labelArticleCount", sysArticleService.getLabelArticleCount(labelId));
        });
        // 根据最新进行降序
        if ("latest".equals(sortType.trim())) {
            logger.debug("按最新排序");
            Collections.sort(list, new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> m1, Map<String, Object> m2) {
                    Integer v1 = Integer.valueOf(m1.get("labelId").toString());
                    Integer v2 = Integer.valueOf(m2.get("labelId").toString());
                    if (v2 != null) {
                        return v2.compareTo(v1);
                    }
                    return 0;
                }
            });
        }
        // 根据最热门进行排序(先关注人数降序，然后文章篇数降序)
        else if ("hottest".equals(sortType.trim())) {
            logger.debug("按最热门排序");
            Collections.sort(list, new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> m1, Map<String, Object> m2) {
                    // 先对关注人数降序排序
                    Integer labelFocusCount1 = Integer.valueOf(m1.get("labelFocusCount").toString());
                    Integer labelFocusCount2 = Integer.valueOf(m2.get("labelFocusCount").toString());
                    int sort1 = labelFocusCount2.compareTo(labelFocusCount1);
                    if (sort1 != 0) {
                        return sort1;
                    }
                    // 接着对文章数降序排序
                    Integer labelArticleCount1 = Integer.valueOf(m1.get("labelArticleCount").toString());
                    Integer labelArticleCount2 = Integer.valueOf(m2.get("labelArticleCount").toString());
                    return labelArticleCount2.compareTo(labelArticleCount1);
                }
            });
        }
        map.put(ResponseConstant.GET_LISTS, list);
        return BaseResult.ok().put(HttpConstant.OK, "搜索成功!", ResponseConstant.DATA, map);
    }

    /**
     * 查询当前登录用户关注的标签
     * @param authentication
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "查询当前登录用户关注的标签-controller")
    @GetMapping("/blog/label/focus/lists")
    public BaseResult getLabelFocusLists(Authentication authentication) throws Exception {
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        List<Map<String, Object>> list = sysLabelMapper.getLabelFocusLists(sysUserId);
        list.stream().forEach(item -> {
            Long labelId = Long.parseLong(item.get("labelId").toString());
            // 关注人数
            item.put("labelFocusCount", otherService.getLabelFocusCount(labelId));
            // 判断当前登录用户是否关注了该标签
            if (otherService.findLabelFocusInfoById(sysUserId, labelId) == true) {
                item.put("isFocus", true);
            } else {
                item.put("isFocus", false);
            }
            // 文章数
            item.put("labelArticleCount", sysArticleService.getLabelArticleCount(labelId));
        });
        Map<String, Object> map = Maps.newHashMap();
        map.put(ResponseConstant.GET_LISTS, list);
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

    /**
     * 取消标签关注
     * @param labelId
     * @param authentication
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "取消标签关注-controller")
    @DeleteMapping("/blog/label/focus/{labelId}")
    public BaseResult deleteLabelFocusOne(@PathVariable("labelId") Long labelId,
                                          Authentication authentication) throws Exception {
        return otherService.deleteLabelFocusOne(labelId, securityOauth2Utils.getUserId(authentication)) <= 0
                ? BaseResult.error(HttpConstant.ERROR_MESSAGE)
                : BaseResult.ok("取消成功");
    }

    /**
     * 添加标签关注
     * @param labelId
     * @param authentication
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "添加标签关注-controller")
    @PostMapping("/blog/label/focus/{labelId}")
    public BaseResult insertLabelFocus(@PathVariable("labelId") Long labelId,
                                       Authentication authentication) throws Exception {
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        SysLabelFocusEntity sysLabelFocusEntity = new SysLabelFocusEntity();
        sysLabelFocusEntity.setSysUserId(sysUserId);
        sysLabelFocusEntity.setLabelId(labelId);
        sysLabelFocusEntity.setCreated(new Date());
        sysLabelFocusEntity.setUpdated(new Date());
        return otherMapper.insertLabelFocus(sysLabelFocusEntity) <= 0
                ? BaseResult.error(HttpConstant.ERROR_MESSAGE)
                : BaseResult.ok("关注成功");
    }

    /**
     * 查看标签下的文章详情
     * @param labelId
     * @param sortType
     * @param authentication
     * @return
     */
    @PrintlnLog(description = "查看标签下的文章详情-controller")
    @GetMapping("/blog/label/details/{labelId}")
    public BaseResult getLabelDetails(@PathVariable("labelId") Long labelId,
                                      String sortType,
                                      Authentication authentication) throws Exception {
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        List<Map<String, Object>> list = sysArticleMapper.getArticleDetailsByLabelId(labelId);
        list.stream().forEach(item -> {
            Long articleId =Long.parseLong(item.get("articleId").toString());
            item.put("articleDetails", sysArticleMapper.getArticlesDetailsToCollection(articleId).get(0));
            // 获取文章点赞数量
            item.put("articlePraiseCount", sysArticlePraiseService.getArticlePraiseCount(articleId));
            // 获取文章评论数量
            item.put("articleCommentsCount", sysArticleCommentsMapper.getArticleCommentsCount(articleId));
            item.put("userNickName", sysUserMapper.findNicknameById(sysUserId));
            // 判断是否当前用户点赞
            item.put("isPraise", sysArticlePraiseService.findInfoById(articleId, sysUserId));
        });
        // 按最新排序
        if ("leatest".equals(sortType.trim())) {
            logger.debug("按最新排序");
            Collections.sort(list, new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> m1, Map<String, Object> m2) {
                    Long articleId1 = Long.valueOf(m1.get("articleId").toString());
                    Long articleId2 = Long.valueOf(m2.get("articleId").toString());
                    if (articleId2 != null) {
                        return articleId2.compareTo(articleId1);
                    }
                    return 0;
                }
            });
        }
        // 按最热门排序(先关注数，被阅读数，评论数降序排序)
        else if ("hostest".equals(sortType.trim())) {
            logger.debug("按最热门排序");
            Collections.sort(list, new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> m1, Map<String, Object> m2) {
                    // 先对关注人数降序排序
                    Integer articlePraiseCount1 = Integer.valueOf(m1.get("articlePraiseCount").toString());
                    Integer articlePraiseCount2 = Integer.valueOf(m2.get("articlePraiseCount").toString());
                    int sort1 = articlePraiseCount2.compareTo(articlePraiseCount1);
                    if (sort1 != 0) {
                        return sort1;
                    }
                    // 接着对被阅读数降序排序
                    Integer articleBrowseSum1 = Integer.valueOf(m1.get("articleBrowseSum").toString());
                    Integer articleBrowseSum2 = Integer.valueOf(m2.get("articleBrowseSum").toString());
                    int sort2 = articleBrowseSum2.compareTo(articleBrowseSum1);
                    if (sort2 != 0) {
                        return sort2;
                    }
                    // 最后对评论数进行降序排序
                    Integer articleCommentsCount1 = Integer.valueOf(m1.get("articleCommentsCount").toString());
                    Integer articleCommentsCount2 = Integer.valueOf(m2.get("articleCommentsCount").toString());
                    return articleCommentsCount2.compareTo(articleCommentsCount1);
                }
            });
        }

        Map<String, Object> map = Maps.newHashMap();
        map.put("articleLists", list);
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

    /**
     * 根据标签id查询标签详情
     * @param labelId
     * @return
     */
    @PrintlnLog(description = "根据标签id查询标签详情-controller")
    @GetMapping("/blog/label/list/{labelId}")
    public BaseResult getLabelList(@PathVariable("labelId") Long labelId,
                                   Authentication authentication) throws Exception{
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        Map<String, Object> map = Maps.newHashMap();
        map.put("labelList", sysLabelMapper.getLabelInfoById(labelId));
        map.put("labelFocusCount", otherService.getLabelFocusCount(labelId));
        map.put("labelArticleCount", sysArticleService.getLabelArticleCount(labelId));
        // 判断当前登录用户是否关注了该标签
        if (otherService.findLabelFocusInfoById(sysUserId, labelId) == true) {
            map.put("isFocus", true);
        } else {
            map.put("isFocus", false);
        }
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }
}

