package lhb.gdms.consumer.blog.service;

import lhb.gdms.commons.base.persistence.BaseService;
import lhb.gdms.commons.domain.entity.SysArticleEntity;

/**
 * @Description  
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/25 
 * @time 13:19
 */
public interface SysArticleService extends BaseService<SysArticleEntity> {

    /**
     * 新增
     * @param entity
     * @return 返回主键id
     */
    public Long insertData(SysArticleEntity entity);

    /**
     * 查询该标签下有多少文章
     * @param labelId
     * @return
     */
    public Integer getLabelArticleCount(Long labelId);
}
