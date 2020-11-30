package lhb.gdms.provider.user.service.impl;

import lhb.gdms.commons.domain.entity.SysUserEntity;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.commons.utils.UUIDUtils;
import lhb.gdms.configuration.aop.config.PrintlnLog;
import lhb.gdms.provider.user.mapper.SysUserMapper;
import lhb.gdms.provider.user.service.PortalLoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PortalLoginServiceImpl implements PortalLoginService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisTokenStore tokenStore;

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 注册
     * @param sysUserEntity
     * @return
     */
    @Override
    public BaseResult portalRegistered(SysUserEntity sysUserEntity) {
        logger.debug(sysUserEntity.toString());
        // 生成默认的用户昵称
        String uuid = "";
        for (int i = 0; i < 5; i++) {
            uuid = UUID.randomUUID().toString();
            uuid = uuid.replace("-", "").substring(0,12);
        }
        String sysUserNickname = "Bin" + uuid;
        sysUserEntity.setSysUserNickname(sysUserNickname);
        sysUserEntity.setSysUserStatus(1);
        sysUserEntity.setSysUserType(1);
        return BaseResult.ok(sysUserNickname);
//        return BaseResult.ok();
    }
}
