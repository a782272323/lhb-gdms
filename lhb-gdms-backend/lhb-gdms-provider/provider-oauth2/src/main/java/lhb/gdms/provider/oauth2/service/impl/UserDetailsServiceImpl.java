package lhb.gdms.provider.oauth2.service.impl;

import lhb.gdms.commons.domain.entity.SysUserEntity;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.feign.user.UserFeign;
import lhb.gdms.provider.oauth2.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Description  登录校验逻辑实现类
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/19
 * @time 23:40
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 判断是什么来源的用户，1-门户网站，2-后台管理网站，3-小程序（小程序还不知道要不要）
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setSysUserUsername(username);
        SysUserEntity entity = sysUserService.selectOneByKeyWord(sysUserEntity);
        log.debug(entity.toString());

        // 门户网站用户登录处理
        if (entity.getSysUserType() == 1) {
            log.debug("门户网站用户登录");
            UserDetails userDetails =
                    User.withUsername(entity.getSysUserUsername())
                            .password(entity.getSysUserPassword())
                            // 授权
                            .authorities("Average User")
                            .build();

            return userDetails;
        }

        // 后台管理网站用户登录处理
        if (entity.getSysUserType() == 2) {
            log.debug("后台管理网站用户登录,oauth2");
            UserDetails userDetails =
                    User.withUsername(entity.getSysUserUsername())
                            .password(entity.getSysUserPassword())
                            // 授权
                            .authorities("System Admin")
                            .build();

            return userDetails;
        }

        return null;
    }

}
