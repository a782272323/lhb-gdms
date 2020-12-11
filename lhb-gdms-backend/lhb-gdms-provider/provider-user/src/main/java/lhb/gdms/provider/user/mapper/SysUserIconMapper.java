package lhb.gdms.provider.user.mapper;

import lhb.gdms.commons.base.persistence.BaseMapper;
import lhb.gdms.commons.domain.entity.SysUserIconEntity;
import org.springframework.stereotype.Repository;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/9
 * @time 17:42
 */
@Repository
public interface SysUserIconMapper extends BaseMapper<SysUserIconEntity> {

    /**
     * 查找系统默认头像相关信息
     * @return
     */
    public SysUserIconEntity findDefaultUrlInfo();
}
