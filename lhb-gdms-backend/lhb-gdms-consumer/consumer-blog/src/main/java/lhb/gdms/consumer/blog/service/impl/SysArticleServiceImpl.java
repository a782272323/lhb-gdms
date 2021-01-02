package lhb.gdms.consumer.blog.service.impl;

import lhb.gdms.commons.base.persistence.BaseServiceImpl;
import lhb.gdms.commons.domain.entity.SysArticleEntity;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.consumer.blog.mapper.SysArticleMapper;
import lhb.gdms.consumer.blog.service.SysArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/25
 * @time 17:17
 */
@Service
public class SysArticleServiceImpl extends BaseServiceImpl<SysArticleEntity, SysArticleMapper> implements SysArticleService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysArticleMapper sysArticleMapper;

    /**
     * 新增
     * @param entity
     * @return 返回主键id
     */
    @Override
    public Long insertData(SysArticleEntity entity) {
        return sysArticleMapper.insertData(entity) > 0
                ? entity.getArticleId()
                : null;
    }

    /**
     * 查询该标签下有多少文章
     * @param labelId
     * @return
     */
    @Override
    public Integer getLabelArticleCount(Long labelId) {
        Integer count = sysArticleMapper.getLabelArticleCount(labelId);
        return count == null ? 0 : count;
    }
}
