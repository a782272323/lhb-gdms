package lhb.gdms.provider.cloud.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class QQMailTest {

    /**
     * 发送简单邮件测试
     */
    @Test
    public void sendSimpleMailTest() {
        // 1.构建一个邮件对象
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        // 2.设置邮件主题

    }
}
