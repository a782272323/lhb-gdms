package lhb.gdms.consumer.blog.service.impl;

import lhb.gdms.commons.base.persistence.BaseServiceImpl;
import lhb.gdms.commons.domain.entity.SysInputHistoryEntity;
import lhb.gdms.consumer.blog.mapper.SysInputHistoryMapper;
import lhb.gdms.consumer.blog.service.SysInputHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2021/1/22
 * @time 23:55
 */
@Service
public class SysInputHistoryServiceImpl extends BaseServiceImpl<SysInputHistoryEntity, SysInputHistoryMapper> implements SysInputHistoryService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysInputHistoryMapper sysInputHistoryMapper;

    /**
     * 获取输入框历史数据列表
     * @param entity
     * @return
     */
    @Override
    public Integer getInputHistoryLists(SysInputHistoryEntity entity) {
        return sysInputHistoryMapper.getInputHistoryLists(entity);
    }

    /**
     * 获取五条数据中最小的id
     * @param sysUserId
     * @return
     */
    @Override
    public Long getMinIdBySysUserId(Long sysUserId) {
        return sysInputHistoryMapper.getMinIdBySysUserId(sysUserId);
    }

    /**
     * 获取输入框历史数据列表
     * @param sysUserId
     * @return
     */
    @Override
    public List<SysInputHistoryEntity> getInputHistoryAll(Long sysUserId) {
        return sysInputHistoryMapper.getInputHistoryAll(sysUserId);
    }

    /**
     * 清空搜索框历史记录
     * @param sysUserId
     * @return
     */
    @Override
    public Integer clearInputHistory(Long sysUserId) {
        Integer resultSum = sysInputHistoryMapper.clearInputHistory(sysUserId);
        return resultSum > 0 ? resultSum : 0;
    }
}
