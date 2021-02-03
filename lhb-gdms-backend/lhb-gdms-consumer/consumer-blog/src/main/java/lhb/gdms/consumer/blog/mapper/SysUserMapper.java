package lhb.gdms.consumer.blog.mapper;

import lhb.gdms.commons.base.persistence.BaseMapper;
import lhb.gdms.commons.domain.entity.SysUserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/18
 * @time 15:23
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUserEntity> {

    /**
     * 根据id查询用户昵称
     * @param sysUserId
     * @return
     */
    String findNicknameById(@Param("sysUserId") Long sysUserId);

    /**
     * 根据用户id查询用户详情
     * @param sysUserId
     * @return
     */
    SysUserEntity getUserInfoByUserId(@Param("sysUserId") Long sysUserId);

    /**
     * 获取主页用户信息通过用户id
     * @param sysUserId
     * @return
     */
    List<Map<String, Object>> getHomeUserInfoById(@Param("sysUserId") Long sysUserId);

    /**
     * 用户主页动态数据
     * @return
     */
    List<Map<String, Object>> getHomeDynamicInfo(@Param("userId") Long userId);

    /**
     * 搜索用户
     * @param sysUserNickname
     * @return
     */
    List<Map<String, Object>> searchUserLists(@Param("sysUserNickname") String sysUserNickname);

    /**
     * 重置密码
     * @param entity
     * @return
     */
    Integer resetPassword(@Param("entity") SysUserEntity entity);

    /**
     * 通过手机号码查询用户信息
     * @param phone
     * @return
     */
    SysUserEntity getUserInfoByPhone(@Param("phone") String phone);

    /**
     * 通过邮箱号码查询用户信息
     * @param email
     * @return
     */
    SysUserEntity getUserInfoByEmail(@Param("email") String email);
}
