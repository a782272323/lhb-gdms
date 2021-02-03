package lhb.gdms.consumer.blog.service;

import lhb.gdms.commons.base.persistence.BaseService;
import lhb.gdms.commons.domain.entity.SysLabelEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description  
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/31 
 * @time 18:57
 */
public interface SysLabelService extends BaseService<SysLabelEntity> {

    /**
     * 查询当前登录用户关注的标签
     * @param sysUserId
     * @return
     */
    public List<Map<String, Object>> getLabelFocusLists(Long sysUserId);

    /**
     * 当前登录用户关注的标签数据列表
     * @param sysUserId
     * @return
     */
    public List<Map<String, Object>> getFocusLabelLists(Long sysUserId);

    /**
     * 根据用户id查询用户关注的标签数
     * @param sysUserId
     * @return
     */
    public Integer getHomeUserFocusLabelCountAll(Long sysUserId);

    /**
     * 搜索标签
     * @param labelName
     * @return
     */
    public List<Map<String, Object>> searchLabelLists(String labelName);
}
