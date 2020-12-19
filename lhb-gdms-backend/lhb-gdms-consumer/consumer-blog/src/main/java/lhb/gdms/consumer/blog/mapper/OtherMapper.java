package lhb.gdms.consumer.blog.mapper;

import lhb.gdms.commons.domain.entity.SysUserEntity;
import lhb.gdms.commons.domain.entity.SysUserIconEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description  调用各种表的 mapper
 * 由于有些表的操作只需要一次，没必要新建个mapper去对接
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/18
 * @time 14:04
 */
@Repository
public interface OtherMapper {

    /**
     * 获取全部系统头像信息
     * @return
     */
    List<SysUserIconEntity> getIconLists();

    /**
     * 根据id查询系统头像信息
     * @param sysUserIconId
     * @return
     */
    SysUserIconEntity getIconInfoById(@Param("sysUserIconId") Long sysUserIconId);
}
