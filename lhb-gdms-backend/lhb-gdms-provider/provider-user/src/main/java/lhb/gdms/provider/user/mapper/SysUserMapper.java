package lhb.gdms.provider.user.mapper;


import lhb.gdms.commons.base.persistence.BaseMapper;
import lhb.gdms.commons.domain.entity.SysUserEntity;
import lhb.gdms.commons.domain.entity.SysUserPhoneEmailEntity;
import lhb.gdms.commons.domain.entity.SysUserRoleEntity;
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

    /**
     * 添加用户角色关系,平台用户暂定一个角色关系
     * @param entity
     * @return
     */
    public Integer insertSysUserRole(@Param("entity")SysUserRoleEntity entity);

    /**
     * 添加角色手机邮箱绑定关系表
     * @param entity
     * @return
     */
    public Integer insertSysUserPhoneEmail(@Param("entity") SysUserPhoneEmailEntity entity);

    /**
     * 校验手机号码是否重复
     * @param sysUserPhone
     * @param sysUserAreaCode
     * @return
     */
    public SysUserEntity checkSysUserPhone(@Param("sysUserPhone") String sysUserPhone,
                                           @Param("sysUserAreaCode") String sysUserAreaCode);

    /**
     * 校验用户名是否重复
     * @param sysUserUsername
     * @return
     */
    public SysUserEntity checkSysUsername(@Param("sysUserUsername") String sysUserUsername);

    /**
     * 校验用户邮箱是否重复
     * @param sysUserEmail
     * @return
     */
    public SysUserEntity checkSysUserEmail(@Param("sysUserEmail") String sysUserEmail);
}
