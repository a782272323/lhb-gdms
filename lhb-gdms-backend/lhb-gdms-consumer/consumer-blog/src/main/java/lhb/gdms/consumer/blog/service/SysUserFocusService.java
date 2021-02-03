package lhb.gdms.consumer.blog.service;

import lhb.gdms.commons.base.persistence.BaseService;
import lhb.gdms.commons.domain.entity.SysUserFocusEntity;

import java.util.List;
import java.util.Map;

/**
 * @Description  
 * @author Herbie Leung(梁鸿斌)
 * @date 2021/1/19 
 * @time 14:57
 */
public interface SysUserFocusService extends BaseService<SysUserFocusEntity> {

    /**
     * 你的关注数据列表
     * @param sysUserId
     * @return
     */
    public List<Map<String, Object>> getFocusPersonLists(Long sysUserId);

    /**
     * 粉丝列表数据
     * @param sysUserId
     * @return
     */
    public List<Map<String, Object>> getFocusFansLists(Long sysUserId);

    /**
     * 校验当前用户是否关注了粉丝
     * @param formSysUserId
     * @param sysUserId
     * @return
     */
    public Boolean checkFocusByTwoId(Long formSysUserId, Long sysUserId);

    /**
     * 获取当前用户对关注了的粉丝的用户关注表详情
     * 当前登录用户是否关注了该粉丝（某一位用户）
     * @param formSysUserId
     * @param sysUserId
     * @return
     */
    public SysUserFocusEntity getFocusInfoByTwoId(Long formSysUserId, Long sysUserId);

    /**
     * 根据用户id查询用户关注的人数
     * @param sysUserId
     * @return
     */
    public Integer getHomeUserFocusPersonCountAll(Long sysUserId);

    /**
     * 根据用户id查询用户粉丝数
     * @param sysUserId
     * @return
     */
    public Integer getHomeUserFocusFansCountAll(Long sysUserId);
}
