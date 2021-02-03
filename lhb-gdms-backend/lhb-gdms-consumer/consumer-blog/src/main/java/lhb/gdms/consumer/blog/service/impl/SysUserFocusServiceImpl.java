package lhb.gdms.consumer.blog.service.impl;

import lhb.gdms.commons.base.persistence.BaseServiceImpl;
import lhb.gdms.commons.domain.entity.SysUserFocusEntity;
import lhb.gdms.consumer.blog.mapper.SysUserFocusMapper;
import lhb.gdms.consumer.blog.service.SysUserFocusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2021/1/19
 * @time 14:58
 */
@Service
public class SysUserFocusServiceImpl extends BaseServiceImpl<SysUserFocusEntity, SysUserFocusMapper> implements SysUserFocusService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysUserFocusMapper sysUserFocusMapper;

    /**
     * 你的关注数据列表
     * @param sysUserId
     * @return
     */
    @Override
    public List<Map<String, Object>> getFocusPersonLists(Long sysUserId) {
        return sysUserFocusMapper.getFocusPersonLists(sysUserId);
    }

    /**
     * 粉丝列表数据
     * @param sysUserId
     * @return
     */
    @Override
    public List<Map<String, Object>> getFocusFansLists(Long sysUserId) {
        List<Map<String, Object>> list = sysUserFocusMapper.getFocusFansLists(sysUserId);
        return list;
    }

    /**
     * 校验当前用户是否关注了粉丝
     * @param formSysUserId
     * @param sysUserId
     * @return
     */
    @Override
    public Boolean checkFocusByTwoId(Long formSysUserId, Long sysUserId) {
        SysUserFocusEntity entity = sysUserFocusMapper.getFocusInfoByTwoId(formSysUserId, sysUserId);
        return entity == null ? false : true;
    }

    /**
     * 获取当前用户对关注了的粉丝的用户关注表详情
     * @param formSysUserId
     * @param sysUserId
     * @return
     */
    @Override
    public SysUserFocusEntity getFocusInfoByTwoId(Long formSysUserId, Long sysUserId) {
        return sysUserFocusMapper.getFocusInfoByTwoId(formSysUserId, sysUserId);
    }

    /**
     * 根据用户id查询用户关注的人数
     * @param sysUserId
     * @return
     */
    @Override
    public Integer getHomeUserFocusPersonCountAll(Long sysUserId) {
        return sysUserFocusMapper.getHomeUserFocusPersonCountAll(sysUserId);
    }

    /**
     * 根据用户id查询用户粉丝数
     * @param sysUserId
     * @return
     */
    @Override
    public Integer getHomeUserFocusFansCountAll(Long sysUserId) {
        return sysUserFocusMapper.getHomeUserFocusFansCountAll(sysUserId);
    }
}
