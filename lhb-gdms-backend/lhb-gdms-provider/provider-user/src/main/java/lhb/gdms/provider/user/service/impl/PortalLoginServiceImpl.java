package lhb.gdms.provider.user.service.impl;

import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.domain.entity.SysUserEntity;
import lhb.gdms.commons.domain.entity.SysUserIconEntity;
import lhb.gdms.commons.domain.entity.SysUserPhoneEmailEntity;
import lhb.gdms.commons.domain.entity.SysUserRoleEntity;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.commons.utils.UUIDUtils;
import lhb.gdms.configuration.aop.config.PrintlnLog;
import lhb.gdms.provider.user.domain.vo.LoginPortalParamVO;
import lhb.gdms.provider.user.mapper.SysUserIconMapper;
import lhb.gdms.provider.user.mapper.SysUserMapper;
import lhb.gdms.provider.user.service.PortalLoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
public class PortalLoginServiceImpl implements PortalLoginService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisTokenStore tokenStore;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserIconMapper sysUserIconMapper;

    /**
     * 注册
     * @param sysUserEntity
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public BaseResult portalRegistered(SysUserEntity sysUserEntity) {

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
        sysUserEntity.setCreated(new Date());
        sysUserEntity.setUpdated(new Date());
        // 获取系统默认头像
        SysUserIconEntity sysUserIconEntity = sysUserIconMapper.findDefaultUrlInfo();
        sysUserEntity.setSysUserIcon(sysUserIconEntity.getUserIconUrl());


        // 把信息添加进入账户表
        sysUserMapper.insert(sysUserEntity);
        logger.debug(sysUserEntity.toString());
        // 赋予平台用户角色
        SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
        sysUserRoleEntity.setSysUserId(sysUserEntity.getSysUserId());
        sysUserRoleEntity.setRoleId((long) 1);
        sysUserRoleEntity.setCreated(new Date());
        sysUserRoleEntity.setUpdated(new Date());
        sysUserMapper.insertSysUserRole(sysUserRoleEntity);

        // 添加角色邮箱手机绑定关系表
        SysUserPhoneEmailEntity sysUserPhoneEmailEntity = new SysUserPhoneEmailEntity();
        sysUserPhoneEmailEntity.setIsPhone(0);
        sysUserPhoneEmailEntity.setIsEmail(0);
        sysUserPhoneEmailEntity.setSysUserId(sysUserEntity.getSysUserId());
        sysUserMapper.insertSysUserPhoneEmail(sysUserPhoneEmailEntity);

        return BaseResult.ok(HttpConstant.REGISTERED_OK_MESSAGE);
    }

    /**
     * 门户网站用户登录
     * @param loginPortalParamVO
     * @return
     */
    @Override
    public BaseResult portalLogin(LoginPortalParamVO loginPortalParamVO) {

        return null;
    }
}
