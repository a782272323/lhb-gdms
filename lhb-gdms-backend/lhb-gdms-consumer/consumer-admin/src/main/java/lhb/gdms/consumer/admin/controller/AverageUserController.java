package lhb.gdms.consumer.admin.controller;

import com.google.common.collect.Maps;
import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.constant.ResponseConstant;
import lhb.gdms.commons.domain.vo.QueryVO;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.configuration.aop.config.PrintlnLog;
import lhb.gdms.configuration.utils.SecurityOauth2Utils;
import lhb.gdms.consumer.admin.domain.vo.AverageUserSearchVO;
import lhb.gdms.consumer.admin.service.ArticleService;
import lhb.gdms.consumer.admin.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Description  用户管理
 * @author Herbie Leung(梁鸿斌)
 * @date 2021/2/3
 * @time 17:22
 */
@RestController
@CrossOrigin
public class AverageUserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SecurityOauth2Utils securityOauth2Utils;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ArticleService articleService;

    /**
     * 用户列表数据详情
     * @param vo
     * @return
     */
    @PrintlnLog(description = "用户列表数据详情-controller")
    @GetMapping("/admin/average/user/lists")
    public BaseResult getUserLists(QueryVO vo) {
        if (vo.getKeyWord().equals("")) {
            vo.setKeyWord("A.sys_user_id");
        }
        Map<String, Object> map = Maps.newHashMap();
        List<Map<String, Object>> list = sysUserService.getUserDetails(vo);
        map.put("userLists", list);
        map.put("count", sysUserService.getUserDetailsCount(vo));
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

    /**
     * 搜索用户
     * @param vo
     * @param searchVO
     * @return
     */
    @PrintlnLog(description = "搜索用户-controller")
    @GetMapping("/admin/average/user/lists/search")
    public BaseResult searchUserLists(QueryVO vo, AverageUserSearchVO searchVO) {
        if (vo.getKeyWord().equals("")) {
            vo.setKeyWord("A.sys_user_id");
        }
        Map<String, Object> map = Maps.newHashMap();
        List<Map<String, Object>> list = sysUserService.searchUserDetails(vo, searchVO);
        map.put("userLists", list);
        map.put("count", sysUserService.searchUserDetailsCount(vo, searchVO));
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }

    /**
     * 根据用户id查看单个用户信息
     * @param sysUserId
     * @return
     */
    @PrintlnLog(description = "根据用户id查看单个用户信息-controller")
    @GetMapping("/admin/average/user/list/{sysUserId}")
    public BaseResult getUserList(@PathVariable("sysUserId") Long sysUserId) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("userList", sysUserService.getUserList(sysUserId));
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.OK_MESSAGE, ResponseConstant.DATA, map);
    }
}
