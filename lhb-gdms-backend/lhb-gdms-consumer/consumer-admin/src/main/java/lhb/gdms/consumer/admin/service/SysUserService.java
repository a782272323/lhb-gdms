package lhb.gdms.consumer.admin.service;

import lhb.gdms.commons.base.persistence.BaseService;
import lhb.gdms.commons.domain.entity.SysUserEntity;
import lhb.gdms.commons.domain.vo.QueryVO;
import lhb.gdms.consumer.admin.domain.vo.AverageUserSearchVO;
import org.springframework.context.annotation.Primary;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2021/2/3
 * @time 17:44
 */
public interface SysUserService extends BaseService<SysUserEntity> {

    /**
     * 根据七牛云key查找是否有用户使用系统头像
     * @param entity
     * @return
     */
    public List<SysUserEntity> findIconByKey(SysUserEntity entity);

    /**
     * 用户列表数据详情
     * @param vo
     * @return
     */
    public List<Map<String, Object>> getUserDetails(QueryVO vo);

    /**
     * 用户列表数据详情总数据量
     * @param vo
     * @return
     */
    public Integer getUserDetailsCount(QueryVO vo);

    /**
     * 搜索用户
     * @param vo
     * @param searchVO
     * @return
     */
    public List<Map<String, Object>> searchUserDetails(QueryVO vo, AverageUserSearchVO searchVO);

    /**
     * 搜索用户总数据量
     * @param vo
     * @param searchVO
     * @return
     */
    public Integer searchUserDetailsCount(QueryVO vo, AverageUserSearchVO searchVO);

    /**
     * 根据用户id查看单个用户信息
     * @param sysUserId
     * @return
     */
    public List<Map<String, Object>> getUserList(Long sysUserId);

    /**
     * 根据用户id查询系统管理员信息
     * @param sysUserId
     * @return
     */
    public List<Map<String, Object>> getSystemAdminInfoById(Long sysUserId);

    /**
     * 门户网站总用户数和总文章数量
     * @return
     */
    public List<Map<String, Object>> getAllUserAndArticleLists();

    /**
     * 按时间段查询新增用户数
     * @param timeType
     * @return
     */
    public Integer getUserCountByTime(String timeType);

    /**
     * 用户增长曲线和文章增长曲线数据
     * @param year
     * @return
     */
    public List<Map<String, Object>> getArticleAndUserCurveLists(String year);

    /**
     * 查看是否有用户关注标签
     * @param labelId
     * @return
     */
    public boolean isLabelUse(Long labelId);
}
