package lhb.gdms.consumer.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lhb.gdms.commons.base.persistence.BaseServiceImpl;
import lhb.gdms.commons.domain.entity.SysUserEntity;
import lhb.gdms.commons.domain.vo.QueryVO;
import lhb.gdms.consumer.admin.domain.vo.AverageUserSearchVO;
import lhb.gdms.consumer.admin.mapper.ArticleMapper;
import lhb.gdms.consumer.admin.mapper.SysUserMapper;
import lhb.gdms.consumer.admin.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/25
 * @time 20:35
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserEntity, SysUserMapper> implements SysUserService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 根据七牛云key查找是否有用户使用系统头像
     * @param entity
     * @return
     */
    @Override
    public List<SysUserEntity> findIconByKey(SysUserEntity entity) {
        return sysUserMapper.findIconByKey(entity);
    }

    /**
     * 用户列表数据详情
     * @param vo
     * @return
     */
    @Override
    public List<Map<String, Object>> getUserDetails(QueryVO vo) {
        switch (vo.getKeyWord()) {
            case "sysUserNickname":
                vo.setKeyWord("sys_user_nickname");
                logger.debug(vo.toString());
                break;
            case "sysUserUsername":
                vo.setKeyWord("sys_user_username");
                break;
            case "sysUserPhone":
                vo.setKeyWord("sys_user_phone");
                break;
            case "sysUserEmail":
                vo.setKeyWord("sys_user_email");
                break;
        }
        PageHelper.startPage(vo.getOffSet(), vo.getLimit());
        return sysUserMapper.getUserDetails(vo);
    }

    /**
     * 用户列表数据详情总数据量
     * @param vo
     * @return
     */
    @Override
    public Integer getUserDetailsCount(QueryVO vo) {
        List<Map<String, Object>> list = sysUserMapper.getUserDetails(vo);
        return list.size() > 0 && list != null ? list.size() : 0;
    }

    /**
     * 搜索用户
     * @param vo
     * @param searchVO
     * @return
     */
    @Override
    public List<Map<String, Object>> searchUserDetails(QueryVO vo, AverageUserSearchVO searchVO) {
        switch (vo.getKeyWord()) {
            case "sysUserNickname":
                vo.setKeyWord("sys_user_nickname");
                logger.debug(vo.toString());
                break;
            case "sysUserUsername":
                vo.setKeyWord("sys_user_username");
                break;
            case "sysUserPhone":
                vo.setKeyWord("sys_user_phone");
                break;
            case "sysUserEmail":
                vo.setKeyWord("sys_user_email");
                break;
        }
        PageHelper.startPage(vo.getOffSet(), vo.getLimit());
        return sysUserMapper.searchUserDetails(vo, searchVO);
    }

    /**
     * 搜索用户总数据量
     * @param vo
     * @param searchVO
     * @return
     */
    @Override
    public Integer searchUserDetailsCount(QueryVO vo, AverageUserSearchVO searchVO) {
        List<Map<String, Object>> list = sysUserMapper.searchUserDetails(vo, searchVO);
        return list.size() > 0 && list != null ? list.size() : 0;
    }

    /**
     * 根据用户id查看单个用户信息
     * @param sysUserId
     * @return
     */
    @Override
    public List<Map<String, Object>> getUserList(Long sysUserId) {
        return sysUserMapper.getUserList(sysUserId);
    }

    /**
     * 根据用户id查询系统管理员信息
     * @param sysUserId
     * @return
     */
    @Override
    public List<Map<String, Object>> getSystemAdminInfoById(Long sysUserId) {
        return sysUserMapper.getSystemAdminInfoById(sysUserId);
    }

    /**
     * 门户网站总用户数和总文章数量
     * @return
     */
    @Override
    public List<Map<String, Object>> getAllUserAndArticleLists() {
        return sysUserMapper.getAllUserAndArticleLists();
    }

    /**
     * 按时间段查询新增用户数
     * @param timeType
     * @return
     */
    @Override
    public Integer getUserCountByTime(String timeType) {
        return sysUserMapper.getUserCountByTime(timeType);
    }

    /**
     * 用户增长曲线和文章增长曲线数据
     * @param year
     * @return
     */
    @Override
    public List<Map<String, Object>> getArticleAndUserCurveLists(String year) {
        String monthStr = "月份";
        List<Map<String, Object>> list = Lists.newArrayList();
        Map<String, Object> map = Maps.newHashMap();

        for (int i = 0; i < 12; i++) {
            String month = "";
            if (i + 1 < 10) {
                month = "0" + String.valueOf(i + 1);
            } else {
                month = String.valueOf(i + 1);
            }
            map = sysUserMapper.getUserListsByYearAndMonth(year, month,month + monthStr);
            map.put("文章数量", articleMapper.getArticleListsByYearAndMonth(year, month));
            list.add(map);
        }
        return list;
    }

    /**
     * 查看是否有用户关注标签
     * @param labelId
     * @return
     */
    @Override
    public boolean isLabelUse(Long labelId) {
        List<Map<String, Object>> list = sysUserMapper.checkLabelInUser(labelId);
        return list != null && list.size() > 0 ? true : false;
    }
}
