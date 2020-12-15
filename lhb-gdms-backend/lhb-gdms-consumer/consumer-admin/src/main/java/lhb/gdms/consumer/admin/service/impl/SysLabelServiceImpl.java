package lhb.gdms.consumer.admin.service.impl;

import lhb.gdms.commons.base.persistence.BaseServiceImpl;
import lhb.gdms.commons.domain.entity.SysLabelEntity;
import lhb.gdms.consumer.admin.mapper.SysLabelMapper;
import lhb.gdms.consumer.admin.service.SysLabelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description  标签管理表 sys_label service 实现类
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/14
 * @time 10:50
 */
@Service
public class SysLabelServiceImpl extends BaseServiceImpl<SysLabelEntity, SysLabelMapper> implements SysLabelService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysLabelMapper labelMapper;

    /**
     * updateLabelName
     * @param entity
     * @return
     */
    @Override
    public Integer updateLabelName(SysLabelEntity entity) {
        return labelMapper.updateLabelName(entity);
    }
}
