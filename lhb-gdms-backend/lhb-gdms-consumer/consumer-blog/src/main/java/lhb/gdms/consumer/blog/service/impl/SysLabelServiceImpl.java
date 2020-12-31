package lhb.gdms.consumer.blog.service.impl;

import lhb.gdms.commons.base.persistence.BaseServiceImpl;
import lhb.gdms.commons.domain.entity.SysLabelEntity;
import lhb.gdms.consumer.blog.mapper.SysLabelMapper;
import lhb.gdms.consumer.blog.service.SysLabelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/31
 * @time 18:40
 */
@Service
public class SysLabelServiceImpl extends BaseServiceImpl<SysLabelEntity, SysLabelMapper> implements SysLabelService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
}
