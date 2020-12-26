package lhb.gdms.consumer.blog.service.impl;

import lhb.gdms.commons.base.persistence.BaseServiceImpl;
import lhb.gdms.commons.domain.entity.SysDraftEntity;
import lhb.gdms.consumer.blog.mapper.SysDraftMapper;
import lhb.gdms.consumer.blog.service.SysDraftService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/24
 * @time 20:52
 */
@Service
public class SysDraftServiceImpl extends BaseServiceImpl<SysDraftEntity, SysDraftMapper> implements SysDraftService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
}
