package lhb.gdms.consumer.blog.mapper;

import lhb.gdms.commons.base.persistence.BaseMapper;
import lhb.gdms.commons.domain.entity.SysLabelEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/31
 * @time 18:38
 */
@Repository
public interface SysLabelMapper extends BaseMapper<SysLabelEntity> {

    /**
     * 获取所有标签详细信息
     * @return
     */
    List<Map<String, Object>> selectAllLabelLists(@Param("keyWord") String keyWord);

    /**
     * 查询当前登录用户关注的标签
     * @param sysUserId
     * @return
     */
    List<Map<String, Object>> getLabelFocusLists(@Param("sysUserId") Long sysUserId);

    /**
     * 根据id查看标签详情
     * @param labelId
     * @return
     */
    List<SysLabelEntity> getLabelInfoById(@Param("labelId") Long labelId);

    /**
     * 当前登录用户关注的标签数据列表
     * @param sysUserId
     * @return
     */
    List<Map<String, Object>> getFocusLabelLists(@Param("sysUserId") Long sysUserId);

    /**
     * 根据用户id查询用户关注的标签数
     * @param sysUserId
     * @return
     */
    Integer getHomeUserFocusLabelCountAll(@Param("sysUserId") Long sysUserId);

    /**
     * 搜索标签
     * @param labelName
     * @return
     */
    List<Map<String, Object>> searchLabelLists(@Param("labelName") String labelName);
}
