package lhb.gdms.consumer.blog.mapper;

import lhb.gdms.commons.domain.entity.SysLabelEntity;
import lhb.gdms.commons.domain.entity.SysLabelFocusEntity;
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

    /**
     * 获取全部标签信息
     * @return
     */
    List<SysLabelEntity> getLabelLists();

    /**
     * 搜索标签信息
     * @param keyWord
     * @return
     */
    List<SysLabelEntity> queryLabelLists(@Param("keyWord") String keyWord);

    /**
     * 查询该标签下有多少人关注
     * @param labelId
     * @return
     */
    Integer getLabelFocusCount(@Param("labelId") Long labelId);

    /**
     * 判断当前登录用户是否关注了该标签
     * @param sysUserId
     * @param labelId
     * @return
     */
    SysLabelFocusEntity findLabelFocusInfoById(@Param("sysUserId") Long sysUserId,
                                               @Param("labelId") Long labelId);

    /**
     * 取消标签关注
     * @param sysUserId
     * @param labelId
     * @return
     */
    Integer deleteLabelFocusOne(@Param("sysUserId") Long sysUserId,
                                @Param("labelId") Long labelId);

    /**
     * 添加标签关注
     * @param entity
     * @return
     */
    Integer insertLabelFocus(@Param("entity") SysLabelFocusEntity entity);
}
