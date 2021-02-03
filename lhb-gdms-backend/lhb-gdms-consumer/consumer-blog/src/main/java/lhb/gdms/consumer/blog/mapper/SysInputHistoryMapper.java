package lhb.gdms.consumer.blog.mapper;

import lhb.gdms.commons.base.persistence.BaseMapper;
import lhb.gdms.commons.domain.entity.SysInputHistoryEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2021/1/22 
 * @time 23:20
 */
@Repository
public interface SysInputHistoryMapper extends BaseMapper<SysInputHistoryEntity> {

    /**
     * 获取输入框历史数据列表数量
     * @param entity
     * @return
     */
    Integer getInputHistoryLists(@Param("entity") SysInputHistoryEntity entity);

    /**
     * 获取五条数据中最小的id
     * @param sysUserId
     * @return
     */
    Long getMinIdBySysUserId(@Param("sysUserId") Long sysUserId);

    /**
     * 获取输入框历史数据列表
     * @param sysUserId
     * @return
     */
    List<SysInputHistoryEntity> getInputHistoryAll(@Param("sysUserId") Long sysUserId);

    /**
     * 清空搜索框历史记录
     * @param sysUserId
     * @return
     */
    Integer clearInputHistory(@Param("sysUserId") Long sysUserId);
}
