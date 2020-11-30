package lhb.gdms.provider.user.mapper;


import lhb.gdms.commons.base.persistence.BaseMapper;
import lhb.gdms.commons.domain.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

/**
 * @Description  表 sys_user mapper
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/21
 * @time 10:34
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUserEntity> {

}
