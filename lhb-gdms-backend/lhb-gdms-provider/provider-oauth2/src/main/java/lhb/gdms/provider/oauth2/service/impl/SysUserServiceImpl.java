package lhb.gdms.provider.oauth2.service.impl;

import lhb.gdms.commons.base.persistence.BaseServiceImpl;
import lhb.gdms.commons.domain.entity.SysUserEntity;
import lhb.gdms.provider.oauth2.mapper.SysUserMapper;
import lhb.gdms.provider.oauth2.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/22
 * @time 09:50
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserEntity, SysUserMapper> implements SysUserService {
}
