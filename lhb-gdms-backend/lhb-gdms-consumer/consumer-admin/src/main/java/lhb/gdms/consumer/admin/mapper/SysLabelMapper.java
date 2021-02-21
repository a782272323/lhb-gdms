package lhb.gdms.consumer.admin.mapper;

import lhb.gdms.commons.base.persistence.BaseMapper;
import lhb.gdms.commons.domain.entity.SysLabelEntity;
import lhb.gdms.commons.domain.vo.QueryVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description  标签管理表 sys_label mapper
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/14
 * @time 10:49
 */
@Repository
public interface SysLabelMapper extends BaseMapper<SysLabelEntity> {

    /**
     * 修改标签名称
     * @param entity
     * @return
     */
    Integer updateLabelName(@Param("entity") SysLabelEntity entity);

    /**
     * 模糊查询
     * @param queryVO
     * @param labelName
     * @return
     */
    List<SysLabelEntity> query(@Param("vo") QueryVO queryVO, @Param("labelName") String labelName);

    /**
     * 模糊查询数据总和
     * @param labelName
     * @return
     */
    Integer queryCount(String labelName);


}
