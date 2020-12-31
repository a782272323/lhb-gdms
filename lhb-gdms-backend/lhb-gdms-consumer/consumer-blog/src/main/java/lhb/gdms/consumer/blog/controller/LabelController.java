package lhb.gdms.consumer.blog.controller;

import lhb.gdms.configuration.utils.SecurityOauth2Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description  标签管理 controller
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/31
 * @time 18:37
 */
@RestController
@CrossOrigin
public class LabelController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SecurityOauth2Utils securityOauth2Utils;

}
