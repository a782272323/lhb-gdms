package lhb.gdms.consumer.admin.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.constant.ResponseConstant;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.configuration.aop.config.PrintlnLog;
import lhb.gdms.consumer.admin.service.ArticleService;
import lhb.gdms.consumer.admin.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Description  首页
 * @author Herbie Leung(梁鸿斌)
 * @date 2021/2/4
 * @time 18:17
 */
@RestController
@CrossOrigin
public class HomeController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ArticleService articleService;

    /**
     * 门户网站总用户数和总文章数量
     * @return
     */
    @PrintlnLog(description = "门户网站总用户数和总文章数量-controller")
    @GetMapping("/admin/home/user/article/all")
    public BaseResult getAllUserAndArticleLists() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("allLists", sysUserService.getAllUserAndArticleLists());
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

    /**
     * 按时间段查询新增用户数
     * @param timeType days weeks months years
     * @return
     */
    @PrintlnLog(description = "按时间段查询新增用户数-controller")
    @GetMapping("/admin/home/user/time")
    public BaseResult getUserCountByTime(String timeType) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("userCount", sysUserService.getUserCountByTime(timeType));
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

    /**
     * 按时间段查询新增文章数
     * @param timeType days weeks months years
     * @return
     */
    @PrintlnLog(description = "按时间段查询新增文章数-controller")
    @GetMapping("/admin/home/article/time")
    public BaseResult getArticleCountByTime(String timeType) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("articleCount", articleService.getArticleCountByTime(timeType));
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

    /**
     * 用户增长曲线和文章增长曲线数据
     * @param veLineYear 年份
     * @return
     */
    @PrintlnLog(description = "用户增长曲线和文章增长曲线数据-controller")
    @GetMapping("/admin/home/article/and/user/curve/lists")
    public BaseResult getArticleAndUserCurveLists(String veLineYear) {
        Map<String, Object> map = Maps.newHashMap();
        List<Map<String, Object>> list = sysUserService.getArticleAndUserCurveLists(veLineYear);

        map.put("articleAndUserVeLineLists", list);
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }
}
