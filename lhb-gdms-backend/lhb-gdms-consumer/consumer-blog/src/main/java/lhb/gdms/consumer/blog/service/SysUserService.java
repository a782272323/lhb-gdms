package lhb.gdms.consumer.blog.service;

import lhb.gdms.commons.base.persistence.BaseService;
import lhb.gdms.commons.domain.entity.SysUserEntity;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/18
 * @time 15:24
 */
public interface SysUserService extends BaseService<SysUserEntity> {

    /**
     * 修改用户基础信息
     * @param entity
     * @return
     */
    public Integer updateUserBaseInfo(SysUserEntity entity);

    /**
     * 根据用户id查询用户详情
     * @param sysUserId
     * @return
     */
    public SysUserEntity getUserInfoByUserId(Long sysUserId);

    /**
     * 获取主页用户信息通过用户id
     * @param sysUserId
     * @return
     */
    public List<Map<String, Object>> getHomeUserInfoById(Long sysUserId);

    /**
     * 用户主页动态数据
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getHomeDynamicInfo(Long userId);

    /**
     * 搜索用户
     * @param sysUserNickname
     * @return
     */
    public List<Map<String, Object>> searchUserLists(String sysUserNickname);

    /**
     * 重置密码
     * @param entity
     * @return
     */
    public Integer resetPassword(SysUserEntity entity);

    /**
     * 通过手机号码查询用户信息
     * @param phone
     * @return
     */
    public SysUserEntity getUserInfoByPhone(String phone);

    /**
     * 通过邮箱号码查询用户信息
     * @param email
     * @return
     */
    public SysUserEntity getUserInfoByEmail(String email);
}
