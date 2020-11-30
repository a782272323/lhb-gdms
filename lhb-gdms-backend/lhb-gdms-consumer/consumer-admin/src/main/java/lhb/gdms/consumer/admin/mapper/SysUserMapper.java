package lhb.gdms.consumer.admin.mapper;

import lhb.gdms.commons.base.persistence.BaseMapper;
import lhb.gdms.commons.domain.entity.SysUserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description  
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/25 
 * @time 20:26
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUserEntity> {

    /**
     * 根据七牛云key查找是否有用户使用系统头像
     * @param entity
     * @return
     */
    List<SysUserEntity> findIconByKey(@Param("entity") SysUserEntity entity);
}
