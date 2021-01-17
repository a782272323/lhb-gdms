package lhb.gdms.consumer.blog.service.impl;

import lhb.gdms.commons.base.persistence.BaseServiceImpl;
import lhb.gdms.commons.domain.entity.SysCommentsReplyEntity;
import lhb.gdms.consumer.blog.mapper.SysCommentsReplyMapper;
import lhb.gdms.consumer.blog.service.SysCommentsReplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2021/1/16
 * @time 11:56
 */
@Service
public class SysCommentsReplyServiceImpl extends BaseServiceImpl<SysCommentsReplyEntity, SysCommentsReplyMapper> implements SysCommentsReplyService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysCommentsReplyMapper sysCommentsReplyMapper;
}
