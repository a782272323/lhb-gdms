package lhb.gdms.consumer.blog.mapper;

import io.lettuce.core.dynamic.annotation.Param;
import lhb.gdms.commons.base.persistence.BaseMapper;
import lhb.gdms.commons.domain.entity.SysUserEntity;
import org.springframework.stereotype.Repository;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/18
 * @time 15:23
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUserEntity> {

    /**
     * 根据id查询用户昵称
     * @param sysUserId
     * @return
     */
    String findNicknameById(@Param("sysUserId") Long sysUserId);

    /**
     * 根据用户id查询用户详情
     * @param sysUserId
     * @return
     */
    SysUserEntity getUserInfoByUserId(@Param("sysUserId") Long sysUserId);
}
