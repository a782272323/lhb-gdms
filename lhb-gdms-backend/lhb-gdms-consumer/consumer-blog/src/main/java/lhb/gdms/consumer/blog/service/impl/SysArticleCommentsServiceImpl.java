package lhb.gdms.consumer.blog.service.impl;

import lhb.gdms.commons.base.persistence.BaseServiceImpl;
import lhb.gdms.commons.domain.entity.SysArticleCommentsEntity;
import lhb.gdms.consumer.blog.mapper.SysArticleCollectionMapper;
import lhb.gdms.consumer.blog.mapper.SysArticleCommentsMapper;
import lhb.gdms.consumer.blog.service.SysArticleCommentsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/31
 * @time 02:17
 */
@Service
public class SysArticleCommentsServiceImpl extends BaseServiceImpl<SysArticleCommentsEntity, SysArticleCommentsMapper> implements SysArticleCommentsService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
}
