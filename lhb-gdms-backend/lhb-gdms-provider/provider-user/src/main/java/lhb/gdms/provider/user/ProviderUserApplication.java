package lhb.gdms.provider.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @Description  启动类
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/21
 * @time 20:13
 */
@SpringBootApplication
@ComponentScan(basePackages = {"lhb.gdms"})
@EnableDiscoveryClient
@MapperScan(basePackages = {"lhb.gdms.provider.user.mapper"})
@EnableFeignClients(basePackages = {"lhb.gdms.feign.*"})
public class ProviderUserApplication {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    RedisTokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    public static void main(String[] args) {
        SpringApplication.run(ProviderUserApplication.class, args);
    }
}
