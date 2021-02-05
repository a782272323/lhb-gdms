package lhb.gdms.consumer.admin.controller;

import com.google.common.collect.Maps;
import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.constant.ResponseConstant;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.configuration.aop.config.PrintlnLog;
import lhb.gdms.configuration.utils.SecurityOauth2Utils;
import lhb.gdms.consumer.admin.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Description  个人信息 - controller
 * @author Herbie Leung(梁鸿斌)
 * @date 2021/2/3
 * @time 23:19
 */
@RestController
@CrossOrigin
public class SystemController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SecurityOauth2Utils securityOauth2Utils;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 个人信息
     * @param authentication
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "个人信息-controller")
    @GetMapping("/admin/system/person/info")
    public BaseResult getPersonInfo(Authentication authentication) throws Exception {
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        List<Map<String, Object>> list = sysUserService.getSystemAdminInfoById(sysUserId);
        Map<String, Object> map = Maps.newHashMap();
        map.put("userInfo", list);
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }
}
