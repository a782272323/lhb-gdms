package lhb.gdms.consumer.blog.service.impl;

import lhb.gdms.commons.domain.entity.SysArticleCommentsEntity;
import lhb.gdms.commons.domain.entity.SysReplyPraiseEntity;
import lhb.gdms.consumer.blog.mapper.OtherMapper;
import lhb.gdms.consumer.blog.mapper.SysArticleCommentsMapper;
import lhb.gdms.consumer.blog.mapper.SysArticleMapper;
import lhb.gdms.consumer.blog.mapper.SysCommentsReplyMapper;
import lhb.gdms.consumer.blog.service.OtherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/18
 * @time 14:05
 */
@Service
public class OtherServiceImpl implements OtherService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OtherMapper otherMapper;

    @Autowired
    private SysArticleMapper sysArticleMapper;

    @Autowired
    private SysCommentsReplyMapper sysCommentsReplyMapper;

    @Autowired
    private SysArticleCommentsMapper sysArticleCommentsMapper;

    /**
     * 查询该标签下有多少人关注
     * @param labelId
     * @return
     */
    @Override
    public Integer getLabelFocusCount(Long labelId) {
        Integer count = otherMapper.getLabelFocusCount(labelId);
        return count == null ? 0 : count;
    }

    /**
     * 判断当前登录用户是否关注了该标签
     * @param sysUserId
     * @param labelId
     * @return
     */
    @Override
    public Boolean findLabelFocusInfoById(Long sysUserId, Long labelId) {
        return otherMapper.findLabelFocusInfoById(sysUserId, labelId) == null ? false : true;
    }

    /**
     * 取消标签关注
     * @param labelId
     * @param sysUserId
     * @return
     */
    @Override
    public Integer deleteLabelFocusOne(Long labelId, Long sysUserId) {
        int i = otherMapper.deleteLabelFocusOne(sysUserId, labelId);
        return i > 0 ? i : 0;
    }

    /**
     * 校验当前评论的点赞是否是当前用户
     * @param sysArticleCommentsId
     * @param sysUserId
     * @return
     */
    @Override
    public Boolean checkCommentsPraiseByTwoId(Long sysArticleCommentsId, Long sysUserId) {
        List<Map<String, Object>> list = otherMapper.getCommentsPraiseByTwoId(sysArticleCommentsId, sysUserId);
        return list.size() > 0 && list != null ? true : false;
    }

    /**
     * 校验当前回复的点赞是否是当前用户
     * @param sysCommentsReplyId
     * @param sysUserId
     * @return
     */
    @Override
    public Boolean checkReplyPraiseByTwoId(Long sysCommentsReplyId, Long sysUserId) {
        List<Map<String, Object>> list = otherMapper.getReplyPraiseInfoByTwoId(sysCommentsReplyId, sysUserId);
        return list.size() > 0 && list != null ? true : false;
    }

    /**
     * 根据文章id统计评论和回复总数
     * @param articleId
     * @return
     */
    @Override
    public Integer getArticleCommentsCountAll(Long articleId) {
        AtomicInteger sum = new AtomicInteger(sysArticleCommentsMapper.getArticleCommentsCount(articleId));
        List<SysArticleCommentsEntity> list = sysArticleCommentsMapper.getCommentsInfoByArticleId(articleId);
        list.stream().forEach(item -> {
            Long sysArticleCommentsId = item.getSysArticleCommentsId();
            sum.addAndGet(sysCommentsReplyMapper.getCommentsReplyCount(sysArticleCommentsId));
        });
        return sum.get();
    }

    /**
     * 回复点赞
     * @param sysCommentsReplyId
     * @param sysUserId
     * @return
     */
    @Override
    public Integer insertReplyPraise(Long sysCommentsReplyId, Long sysUserId) {
        SysReplyPraiseEntity entity = new SysReplyPraiseEntity();
        entity.setSysCommentsReplyId(sysCommentsReplyId);
        entity.setSysUserId(sysUserId);
        entity.setCreated(new Date());
        entity.setUpdated(new Date());
        return otherMapper.insertReplyPraise(entity) > 0 ? 1 : 0;
    }

    /**
     * 取消回复的点赞
     * @param sysCommentsReplyId
     * @param sysUserId
     * @return
     */
    @Override
    public Integer deleteReplyPraise(Long sysCommentsReplyId, Long sysUserId) {
        return otherMapper.deleteReplyPraise(sysCommentsReplyId, sysUserId) > 0 ? 1 : 0;
    }

    /**
     * 用户主页点赞列表
     * @param userId
     * @return
     */
    @Override
    public List<Map<String, Object>> getHomePraiseLists(Long userId) {
        return otherMapper.getHomePraiseLists(userId);
    }

    /**
     * 消息模块评论相关列表详情
     * @param sysUserId
     * @return
     */
    @Override
    public List<Map<String, Object>> getMessageCommentsLists(Long sysUserId) {
        return otherMapper.getMessageCommentsLists(sysUserId);
    }

    /**
     * 消息模块点赞相关列表详情
     * @param sysUserId
     * @return
     */
    @Override
    public List<Map<String, Object>> getMessagePraiseLists(Long sysUserId) {
        return otherMapper.getMessagePraiseLists(sysUserId);
    }
}
