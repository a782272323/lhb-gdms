package lhb.gdms.provider.oauth2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProviderOauth2Test {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void passwordTest() {
        String string = "123456";
        String password = passwordEncoder.encode(string);
        System.out.println(password);
    }
}
