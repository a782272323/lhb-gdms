package lhb.gdms.provider.oauth2.mapper;

import lhb.gdms.commons.base.persistence.BaseMapper;
import lhb.gdms.commons.domain.entity.SysUserEntity;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

/**
 * @Description  用户服务的mapper
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/22
 * @time 09:47
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUserEntity> {
}
