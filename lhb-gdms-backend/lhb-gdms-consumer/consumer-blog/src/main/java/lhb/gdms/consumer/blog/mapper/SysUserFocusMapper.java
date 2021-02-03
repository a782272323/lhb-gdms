package lhb.gdms.consumer.blog.mapper;

import lhb.gdms.commons.base.persistence.BaseMapper;
import lhb.gdms.commons.domain.entity.SysUserFocusEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description  
 * @author Herbie Leung(梁鸿斌)
 * @date 2021/1/19 
 * @time 14:57
 */
@Repository
public interface SysUserFocusMapper extends BaseMapper<SysUserFocusEntity> {

    /**
     * 你的关注数据列表
     * @param sysUserId
     * @return
     */
    List<Map<String, Object>> getFocusPersonLists(@Param("sysUserId") Long sysUserId);

    /**
     * 粉丝数据列表
     * @param sysUserId
     * @return
     */
    List<Map<String, Object>> getFocusFansLists(@Param("sysUserId") Long sysUserId);

    /**
     * 根据两个id查询用户关注表信息
     * 当前登录用户是否关注了该粉丝（某一位用户）
     * @param formSysUserId
     * @param sysUserId
     * @return
     */
    SysUserFocusEntity getFocusInfoByTwoId(@Param("formSysUserId") Long formSysUserId,
                                           @Param("sysUserId") Long sysUserId);

    /**
     * 根据用户id查询用户关注的人数
     * @param sysUserId
     * @return
     */
    Integer getHomeUserFocusPersonCountAll(@Param("sysUserId") Long sysUserId);

    /**
     * 根据用户id查询用户的粉丝数
     * @param sysUserId
     * @return
     */
    Integer getHomeUserFocusFansCountAll(@Param("sysUserId") Long sysUserId);
}
