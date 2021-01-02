package lhb.gdms.consumer.blog.service;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/18
 * @time 14:05
 */
public interface OtherService {

    /**
     * 查询该标签下有多少人关注
     * @param labelId
     * @return
     */
    public Integer getLabelFocusCount(Long labelId);

    /**
     * 判断当前登录用户是否关注了该标签
     * @param sysUserId
     * @param labelId
     * @return
     */
    public Boolean findLabelFocusInfoById(Long sysUserId, Long labelId);

    /**
     * 取消标签关注
     * @param labelId
     * @param sysUserId
     * @return
     */
    public Integer deleteLabelFocusOne(Long labelId, Long sysUserId);
}
