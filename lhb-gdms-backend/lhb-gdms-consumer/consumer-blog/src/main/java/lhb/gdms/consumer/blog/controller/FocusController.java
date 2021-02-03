package lhb.gdms.consumer.blog.controller;

import com.google.common.collect.Maps;
import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.constant.ResponseConstant;
import lhb.gdms.commons.domain.entity.SysUserEntity;
import lhb.gdms.commons.domain.entity.SysUserFocusEntity;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.configuration.aop.config.PrintlnLog;
import lhb.gdms.configuration.utils.SecurityOauth2Utils;
import lhb.gdms.consumer.blog.service.SysLabelService;
import lhb.gdms.consumer.blog.service.SysUserFocusService;
import lhb.gdms.consumer.blog.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description  关注
 * @author Herbie Leung(梁鸿斌)
 * @date 2021/1/19
 * @time 11:50
 */
@RestController
@CrossOrigin
public class FocusController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SecurityOauth2Utils securityOauth2Utils;

    @Autowired
    private SysUserFocusService sysUserFocusService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysLabelService sysLabelService;

    /**
     * 你的关注数据列表
     * @param authentication
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "你的关注数据列表-controller")
    @GetMapping("/blog/focus/person/lists")
    public BaseResult getFocusPersonLists(Authentication authentication) throws Exception{
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        Map<String, Object> map = Maps.newHashMap();
        List<Map<String, Object>> list = sysUserFocusService.getFocusPersonLists(sysUserId);
        map.put("focusPersonLists", list);
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

    /**
     * 获取当前登录用户详情
     * @param authentication
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "获取当前登录用户详情-controller")
    @GetMapping("/blog/focus/user/lists")
    public BaseResult getFocusUserLists(Authentication authentication) throws Exception{
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        Map<String, Object> map = Maps.newHashMap();
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setSysUserId(sysUserId);
        map.put("focusUserLists", sysUserService.selectOneById(sysUserEntity));
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

    /**
     * 当前登录用户关注的标签数据列表
     * @param authentication
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "当前登录用户关注的标签数据列表-controller")
    @GetMapping("/blog/focus/label/lists")
    public BaseResult getFocusLabelLists(Authentication authentication) throws Exception {
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        Map<String, Object> map = Maps.newHashMap();
        map.put("focusLabelLists", sysLabelService.getFocusLabelLists(sysUserId));
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

    /**
     * 粉丝数据列表
     * @param authentication
     * @return
     * @throws Exception
     */
    @PrintlnLog(description = "粉丝数据列表-controller")
    @GetMapping("/blog/focus/fans/lists")
    public BaseResult getFocusFansLists(Authentication authentication) throws Exception {
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        List<Map<String, Object>> list = sysUserFocusService.getFocusFansLists(sysUserId);
        list.stream().forEach(item -> {
            Long formSysUserId = Long.parseLong(item.get("sysUserId").toString());
            // 当前登录用户是否关注了该粉丝
            boolean isFocus = sysUserFocusService.checkFocusByTwoId(formSysUserId, sysUserId);
            item.put("isFocus", isFocus);
            // 获取当前用户对关注了的粉丝的用户关注表主键id
            if (isFocus == true) {
                item.put("trueUserFocusId", sysUserFocusService.getFocusInfoByTwoId(formSysUserId, sysUserId).getSysUserFocusId());
            }
        });
        Map<String, Object> map = Maps.newHashMap();
        map.put("focusFansLists", list);
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

    /**
     * 取消对用户的关注
     * @param sysUserFocusId
     * @return
     */
    @PrintlnLog(description = "取消对用户的关注-controller")
    @DeleteMapping("/blog/focus/person/{sysUserFocusId}")
    public BaseResult deleteFocusPerson(@PathVariable("sysUserFocusId") Long sysUserFocusId) {
        return sysUserFocusService.deleteOneById(sysUserFocusId) > 0
                ? BaseResult.ok("取消关注成功!")
                : BaseResult.error(HttpConstant.ERROR_MESSAGE);
    }

    /**
     * 关注用户
     * @param formSysUserId
     * @param authentication
     * @return
     */
    @PrintlnLog(description = "关注用户-controller")
    @PostMapping("/blog/focus/person/{formSysUserId}")
    public BaseResult insertFocusPerson(@PathVariable("formSysUserId") Long formSysUserId,
                                        Authentication authentication) throws Exception {
        Long sysUserId = securityOauth2Utils.getUserId(authentication);
        SysUserFocusEntity entity = new SysUserFocusEntity();
        entity.setFormSysUserId(formSysUserId);
        entity.setSysUserId(sysUserId);
        entity.setCreated(new Date());
        entity.setUpdated(new Date());
        return sysUserFocusService.insert(entity) > 0
                ? BaseResult.ok("关注成功!")
                : BaseResult.error(HttpConstant.ERROR_MESSAGE);
    }
}
