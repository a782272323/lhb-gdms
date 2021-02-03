package lhb.gdms.consumer.blog.service.impl;

import lhb.gdms.commons.base.persistence.BaseServiceImpl;
import lhb.gdms.commons.domain.entity.SysUserEntity;
import lhb.gdms.consumer.blog.mapper.SysUserMapper;
import lhb.gdms.consumer.blog.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/18
 * @time 15:24
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserEntity, SysUserMapper> implements SysUserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    String introduction = "该用户很懒，没有写简介";

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 修改用户基础信息
     * @param entity
     * @return
     */
    @Override
    public Integer updateUserBaseInfo(SysUserEntity entity) {
        SysUserEntity sysUserEntity = sysUserMapper.selectOneById(entity);
        if (sysUserEntity.getSysUserIntroduction().trim().equals(introduction) == true && StringUtils.isNotBlank(sysUserEntity.getSysUserIntroduction())) {
            entity.setSysUserIntroduction(introduction);
        }
        entity.setUpdated(new Date());
        return sysUserMapper.update(entity);
    }

    /**
     * 根据用户id查询用户详情
     * @param sysUserId
     * @return
     */
    @Override
    public SysUserEntity getUserInfoByUserId(Long sysUserId) {
        return sysUserMapper.getUserInfoByUserId(sysUserId);
    }

    /**
     * 获取主页用户信息通过用户id
     * @param sysUserId
     * @return
     */
    @Override
    public List<Map<String, Object>> getHomeUserInfoById(Long sysUserId) {
        return sysUserMapper.getHomeUserInfoById(sysUserId);
    }

    /**
     * 用户主页动态数据
     * @param userId
     * @return
     */
    @Override
    public List<Map<String, Object>> getHomeDynamicInfo(Long userId) {
        return sysUserMapper.getHomeDynamicInfo(userId);
    }

    /**
     * 搜索用户
     * @param sysUserNickname
     * @return
     */
    @Override
    public List<Map<String, Object>> searchUserLists(String sysUserNickname) {
        return sysUserMapper.searchUserLists(sysUserNickname);
    }

    /**
     * 重置密码
     * @param entity
     * @return
     */
    @Override
    public Integer resetPassword(SysUserEntity entity) {
        return sysUserMapper.resetPassword(entity);
    }

    /**
     * 通过手机号码查询用户信息
     * @param phone
     * @return
     */
    @Override
    public SysUserEntity getUserInfoByPhone(String phone) {
        return sysUserMapper.getUserInfoByPhone(phone);
    }

    /**
     * 通过邮箱号码查询用户信息
     * @param email
     * @return
     */
    @Override
    public SysUserEntity getUserInfoByEmail(String email) {
        return sysUserMapper.getUserInfoByEmail(email);
    }

}
