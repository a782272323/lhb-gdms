package lhb.gdms.consumer.blog.mapper;

import lhb.gdms.commons.base.persistence.BaseMapper;
import lhb.gdms.commons.domain.entity.SysDraftEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description  
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/24 
 * @time 20:51
 */
@Repository
public interface SysDraftMapper extends BaseMapper<SysDraftEntity> {

    /**
     * 获取草稿箱信息
     * @param sysUserId
     * @return
     */
    List<SysDraftEntity> getDraftLists(@Param("sysUserId") Long sysUserId);
}
