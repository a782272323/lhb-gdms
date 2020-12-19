package lhb.gdms.provider.user.controller;

import com.google.common.collect.Maps;
import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.constant.ResponseConstant;
import lhb.gdms.commons.domain.entity.SysUserEntity;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.configuration.aop.config.PrintlnLog;
import lhb.gdms.provider.user.mapper.SysUserMapper;
import lhb.gdms.provider.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Herbie Leung(梁鸿斌)
 * @Description 用户相关信息查询 controller
 * @date 2020/11/21
 * @time 10:25
 */
@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    @PrintlnLog(description = "根据用户名查询用户信息-controller")
    @GetMapping("/user/selectOne/username")
    public BaseResult selectOneByUsername(@RequestParam String username) {
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setSysUserUsername(username);
        Map<String, Object> map = Maps.newHashMap();
        map.put("list", sysUserService.selectOneByKeyWord(sysUserEntity));
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

    /**
     * 修改用户头像信息
     *
     * @param entity
     * @return
     */
    @PrintlnLog(description = "修改用户头像信息-controller")
    @PutMapping("/user/sysIcon")
    public BaseResult updateUserSysIcon(@RequestBody SysUserEntity entity) {
        if (sysUserService.updateUserSysIcon(entity) > 0) {
            return BaseResult.ok(HttpConstant.EDIT_MESSAGE);
        }
        return BaseResult.error(HttpConstant.ERROR_MESSAGE);
    }

    /**
     * 根据id查询用户信息
     *
     * @param id
     * @return
     */
    @PrintlnLog(description = "根据id查询用户信息-controller")
    @GetMapping("/user/list")
    public BaseResult getList(@RequestParam("id") Long id) {
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setSysUserId(id);
        Map<String, Object> map = Maps.newHashMap();
        map.put(ResponseConstant.GET_LISTS, sysUserService.selectOneById(sysUserEntity));
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

    /**
     * 修改用户基础信息
     * @param entity
     * @return
     */
    @PrintlnLog(description = "修改用户基础信息-controller")
    @PutMapping("/user/base/info")
    public BaseResult updateUserBaseInfo(@RequestBody SysUserEntity entity) {
        if (sysUserService.updateUserBaseInfo(entity) < 0) {
            return BaseResult.error(HttpConstant.EDIT_ERROR_MESSAGE);
        }
        return BaseResult.ok(HttpConstant.EDIT_MESSAGE);
    }
}
