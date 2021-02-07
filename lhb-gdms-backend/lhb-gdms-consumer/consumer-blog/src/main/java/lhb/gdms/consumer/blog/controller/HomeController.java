package lhb.gdms.consumer.blog.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.constant.ResponseConstant;
import lhb.gdms.commons.domain.entity.SysLabelEntity;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.configuration.aop.config.PrintlnLog;
import lhb.gdms.configuration.utils.SecurityOauth2Utils;
import lhb.gdms.consumer.blog.service.SysArticleService;
import lhb.gdms.consumer.blog.service.SysLabelService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description  首页controller
 * @author Herbie Leung(梁鸿斌)
 * @date 2021/1/17
 * @time 12:30
 */
@RestController
@CrossOrigin
public class HomeController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SecurityOauth2Utils securityOauth2Utils;

    @Autowired
    private SysLabelService sysLabelService;

    @Autowired
    private SysArticleService sysArticleService;

    /**
     * 获取当前登录用户已关注标签
     * @param authentication
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "获取当前登录用户已关注标签-controller")
    @GetMapping("/blog/home/user/label/focus")
    public BaseResult getUserLabelFocus(String isMore,
                                        Authentication authentication) throws Exception{
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        List<Map<String, Object>> list = sysLabelService.getLabelFocusLists(sysUserId);
        int length = 6;
        String falseStr = "false";
        String trueStr = "true";

        Map<String, Object> map = Maps.newHashMap();
        if (isMore.equals(falseStr)) {
            map.put("labelFocusLists", list.stream().limit(length));
        }
        if (isMore.equals(trueStr)) {
            map.put("labelFocusLists", list);
        }
        map.put("labelLength", list.size());
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

    /**
     * 首页文章列表信息
     * @param labelType
     * @param sortType leatest-最新 hostest-最热 若focus则sortType=none
     * @param sysUserId 没登录时 值为-1
     * @return
     */
    @PrintlnLog(description = "首页文章列表信息-controller")
    @GetMapping("/web/blog/home/article/{sysUserId}")
    public BaseResult getHomeArticle(@PathVariable("sysUserId") Long sysUserId,
                                     String labelType, String sortType) {
        String recommended = "recommended";
        String focus = "focus";
        String leatest = "leatest";
        String hostest = "hostest";
        String none = "none";
        Long i = Long.valueOf(-1);

        List<Map<String, Object>> list = Lists.newArrayList();
        Map<String, Object> map = Maps.newHashMap();

        // 推荐
        if (labelType.equals(recommended)) {
            // 最新:根据发布时间排序
            if (sortType.equals(leatest)) {
                list = sysArticleService.getHomeArticleLists(sysUserId);
            }
            // 最热门：根据文章被阅读数,点赞数,评论数排序
            else if (sortType.equals(hostest)) {
                list = sysArticleService.getHomeArticlesListsByHostest(sysUserId);
            }
        }
        // 关注
        else if (labelType.equals(focus) && sysUserId.longValue() != i.longValue()) {
            list = sysArticleService.getUserFocusArticleLists(sysUserId);
        }
        // 动态标签
        else if (StringUtils.isNotBlank(labelType)) {
            SysLabelEntity sysLabelEntity = new SysLabelEntity();
            sysLabelEntity.setLabelName(labelType);
            SysLabelEntity entity = sysLabelService.selectOneByKeyWord(sysLabelEntity);
            // 最新:根据发布时间排序
            if (sortType.equals(leatest)) {
                list = sysArticleService.getHomeArticlesListsByLabelId(entity.getLabelId(), sysUserId);
            }
            // 最热门：根据文章被阅读数,点赞数,评论数排序
            else if (sortType.equals(hostest)) {
                list = sysArticleService.getHomeArticlesListsByLabelIdByHostest(entity.getLabelId(), sysUserId);
            }
        }
        // 封装数据返回前端
        map.put("homeArticleLists", list);
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

}
