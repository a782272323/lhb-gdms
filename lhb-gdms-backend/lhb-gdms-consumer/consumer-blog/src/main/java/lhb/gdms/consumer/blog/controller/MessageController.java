package lhb.gdms.consumer.blog.controller;

import com.google.common.collect.Maps;
import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.constant.ResponseConstant;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.configuration.aop.config.PrintlnLog;
import lhb.gdms.configuration.utils.SecurityOauth2Utils;
import lhb.gdms.consumer.blog.service.OtherService;
import lhb.gdms.consumer.blog.service.SysArticleCommentsService;
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

/**
 * @Description  消息模块 controller
 * @author Herbie Leung(梁鸿斌)
 * @date 2021/2/1
 * @time 22:35
 */
@RestController
@CrossOrigin
public class MessageController {
    final private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SecurityOauth2Utils securityOauth2Utils;

    @Autowired
    private SysArticleCommentsService sysArticleCommentsService;

    @Autowired
    private OtherService otherService;

    /**
     * 评论相关详情列表
     * @param authentication
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "评论相关详情列表-controller")
    @GetMapping("/blog/message/comment/lists")
    public BaseResult getMessageCommentsLists(Authentication authentication) throws Exception {
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        Map<String, Object> map = Maps.newHashMap();
        List<Map<String, Object>> list = otherService.getMessageCommentsLists(sysUserId);

        map.put("commentsLists", list);
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

    /**
     * 点赞相关详情列表
     * @param authentication
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "点赞相关详情列表-controller")
    @GetMapping("/blog/message/praise/lists")
    public BaseResult getMessagePraiseLists(Authentication authentication) throws Exception {
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        Map<String, Object> map = Maps.newHashMap();
        List<Map<String, Object>> list = otherService.getMessagePraiseLists(sysUserId);
        map.put("praiseLists", list);
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

}
