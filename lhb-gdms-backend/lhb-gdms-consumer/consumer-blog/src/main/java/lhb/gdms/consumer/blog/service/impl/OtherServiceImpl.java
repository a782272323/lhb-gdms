package lhb.gdms.consumer.blog.service.impl;

import lhb.gdms.consumer.blog.mapper.OtherMapper;
import lhb.gdms.consumer.blog.service.OtherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/18
 * @time 14:05
 */
@Service
public class OtherServiceImpl implements OtherService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OtherMapper otherMapper;

    /**
     * 查询该标签下有多少人关注
     * @param labelId
     * @return
     */
    @Override
    public Integer getLabelFocusCount(Long labelId) {
        Integer count = otherMapper.getLabelFocusCount(labelId);
        return count == null ? 0 : count;
    }

    /**
     * 判断当前登录用户是否关注了该标签
     * @param sysUserId
     * @param labelId
     * @return
     */
    @Override
    public Boolean findLabelFocusInfoById(Long sysUserId, Long labelId) {
        return otherMapper.findLabelFocusInfoById(sysUserId, labelId) == null ? false : true;
    }

    /**
     * 取消标签关注
     * @param labelId
     * @param sysUserId
     * @return
     */
    @Override
    public Integer deleteLabelFocusOne(Long labelId, Long sysUserId) {
        int i = otherMapper.deleteLabelFocusOne(sysUserId, labelId);
        return i > 0 ? i : 0;
    }
}
