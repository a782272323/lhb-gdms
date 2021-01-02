package lhb.gdms.consumer.blog.controller;

import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.configuration.aop.config.PrintlnLog;
import lhb.gdms.configuration.utils.SecurityOauth2Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2021/1/2
 * @time 12:56
 */
@RestController
@CrossOrigin
public class ArticleController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SecurityOauth2Utils securityOauth2Utils;

    /**
     * 文章被点击查看时被阅读数加
     * @param articleId
     * @return
     */
    @PrintlnLog(description = "文章被点击查看时被阅读数加一-controller")
    @PostMapping("/web/blog/article/browse/{articleId}")
    public BaseResult insertArticleBrowse(@PathVariable("articleId") Long articleId) {
        return BaseResult.ok();
    }
}
