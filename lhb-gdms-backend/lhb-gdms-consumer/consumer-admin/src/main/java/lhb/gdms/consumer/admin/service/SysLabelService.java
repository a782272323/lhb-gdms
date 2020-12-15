package lhb.gdms.consumer.admin.service;

import lhb.gdms.commons.base.persistence.BaseService;
import lhb.gdms.commons.domain.entity.SysLabelEntity;

/**
 * @Description  标签管理表 sys_label service
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/14
 * @time 10:50
 */
public interface SysLabelService extends BaseService<SysLabelEntity> {

    /**
     * 修改标签名称
     * @param entity
     * @return
     */
    public Integer updateLabelName(SysLabelEntity entity);
}
