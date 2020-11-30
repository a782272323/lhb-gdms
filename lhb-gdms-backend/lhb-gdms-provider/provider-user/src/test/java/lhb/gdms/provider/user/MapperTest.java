package lhb.gdms.provider.user;

import lhb.gdms.commons.domain.entity.SysUserEntity;

import lhb.gdms.provider.user.service.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;


import javax.annotation.Resource;
import java.text.ParseException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MapperTest {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void selectOneByName() throws ParseException {
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setSysUserUsername("lhb");
        SysUserEntity entity = sysUserService.selectOneByKeyWord(sysUserEntity);
        System.out.println(entity.getCreated());

        if (passwordEncoder.matches("123456", entity.getSysUserPassword())) {
            System.out.println("密码一样");
        }
    }
}
