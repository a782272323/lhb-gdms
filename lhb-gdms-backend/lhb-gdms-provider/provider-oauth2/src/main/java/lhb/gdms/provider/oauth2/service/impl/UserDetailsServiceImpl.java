package lhb.gdms.provider.oauth2.service.impl;

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // todo 暂时模拟用户登录
        String password = passwordEncoder.encode("123456");
        log.trace("password = {}", password);
        System.out.println("password = " + password);
        UserDetails userDetails =
                User.withUsername("lhb")
                        .password(password)
                        .authorities("ROLE_USER1")
                        .build();
        return userDetails;
    }

}
