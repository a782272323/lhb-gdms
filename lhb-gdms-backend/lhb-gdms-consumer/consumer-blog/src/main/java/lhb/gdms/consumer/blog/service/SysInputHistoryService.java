package lhb.gdms.consumer.blog.service;

import lhb.gdms.commons.base.persistence.BaseService;
import lhb.gdms.commons.domain.entity.SysInputHistoryEntity;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2021/1/22
 * @time 23:24
 */
public interface SysInputHistoryService extends BaseService<SysInputHistoryEntity> {

    /**
     * 获取输入框历史数据列表
     * @param entity
     * @return
     */
    public Integer getInputHistoryLists(SysInputHistoryEntity entity);

    /**
     * 获取五条数据中最小的id
     * @param sysUserId
     * @return
     */
    public Long getMinIdBySysUserId(Long sysUserId);

    /**
     * 获取输入框历史数据列表
     * @param sysUserId
     * @return
     */
    public List<SysInputHistoryEntity> getInputHistoryAll(Long sysUserId);

    /**
     * 清空搜索框历史记录
     * @param sysUserId
     * @return
     */
    public Integer clearInputHistory(Long sysUserId);
}
