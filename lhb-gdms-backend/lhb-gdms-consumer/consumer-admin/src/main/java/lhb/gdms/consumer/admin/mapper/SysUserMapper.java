package lhb.gdms.consumer.admin.mapper;

import lhb.gdms.commons.base.persistence.BaseMapper;
import lhb.gdms.commons.domain.entity.SysUserEntity;
import lhb.gdms.commons.domain.vo.QueryVO;
import lhb.gdms.consumer.admin.domain.vo.AverageUserSearchVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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

    /**
     * 用户列表数据详情
     * @param vo
     * @return
     */
    List<Map<String, Object>> getUserDetails(@Param("vo") QueryVO vo);

    /**
     * 搜索用户
     * @param queryVO
     * @param searchVO
     * @return
     */
    List<Map<String, Object>> searchUserDetails(@Param("queryVO") QueryVO queryVO,
                                                @Param("searchVO") AverageUserSearchVO searchVO);

    /**
     * 根据用户id查看单个用户信息
     * @param sysUserId
     * @return
     */
    List<Map<String, Object>> getUserList(@Param("sysUserId") Long sysUserId);

    /**
     * 根据用户id查询系统管理员信息
     * @param sysUserId
     * @return
     */
    List<Map<String, Object>> getSystemAdminInfoById(@Param("sysUserId") Long sysUserId);

    /**
     * 门户网站总用户数和总文章数量
     * @return
     */
    List<Map<String, Object>> getAllUserAndArticleLists();

    /**
     * 按时间段查询新增用户数
     * @param timeType
     * @return
     */
    Integer getUserCountByTime(@Param("timeType") String timeType);

    /**
     * 根据年和月获取用户数量
     * @param year
     * @param month
     * @param monthStr
     * @return
     */
    Map<String, Object> getUserListsByYearAndMonth(@Param("year") String year,
                                                         @Param("month") String month,
                                                         @Param("monthStr") String monthStr);

    /**
     * 查看是否有用户关注标签
     * @param labelId
     * @return
     */
    List<Map<String, Object>> checkLabelInUser(@Param("labelId") Long labelId);
}
