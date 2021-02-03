package lhb.gdms.consumer.blog.service.impl;

import lhb.gdms.commons.base.persistence.BaseServiceImpl;
import lhb.gdms.commons.domain.entity.SysLabelEntity;
import lhb.gdms.consumer.blog.mapper.SysLabelMapper;
import lhb.gdms.consumer.blog.service.SysLabelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/31
 * @time 18:40
 */
@Service
public class SysLabelServiceImpl extends BaseServiceImpl<SysLabelEntity, SysLabelMapper> implements SysLabelService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysLabelMapper sysLabelMapper;

    /**
     * 查询当前登录用户关注的标签
     * @param sysUserId
     * @return
     */
    @Override
    public List<Map<String, Object>> getLabelFocusLists(Long sysUserId) {
        return sysLabelMapper.getLabelFocusLists(sysUserId);
    }

    /**
     * 当前登录用户关注的标签数据列表
     * @param sysUserId
     * @return
     */
    @Override
    public List<Map<String, Object>> getFocusLabelLists(Long sysUserId) {
        return sysLabelMapper.getFocusLabelLists(sysUserId);
    }

    /**
     * 根据用户id查询用户关注的标签数
     * @param sysUserId
     * @return
     */
    @Override
    public Integer getHomeUserFocusLabelCountAll(Long sysUserId) {
        return sysLabelMapper.getHomeUserFocusLabelCountAll(sysUserId);
    }

    /**
     * 搜索标签
     * @param labelName
     * @return
     */
    @Override
    public List<Map<String, Object>> searchLabelLists(String labelName) {
        return sysLabelMapper.searchLabelLists(labelName);
    }
}
