package lhb.gdms.provider.user.controller;

import com.google.common.collect.Maps;
import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.constant.ResponseConstant;
import lhb.gdms.commons.domain.entity.SysUserEntity;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.provider.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Description  用户相关信息查询 controller
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/21
 * @time 10:25
 */
@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    @GetMapping("/user/selectOne/username")
    public BaseResult selectOneByUsername(@RequestParam String username) {
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setSysUserUsername(username);
        Map<String, Object> map = Maps.newHashMap();
        map.put("list", sysUserService.selectOneByKeyWord(sysUserEntity));
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

}
