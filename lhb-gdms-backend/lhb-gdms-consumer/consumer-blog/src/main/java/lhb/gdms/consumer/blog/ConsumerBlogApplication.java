package lhb.gdms.consumer.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description  启动类
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/20
 * @time 16:49
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"lhb.gdms"})
@EnableFeignClients(basePackages = {"lhb.gdms.feign.*"})
@MapperScan(basePackages = {"lhb.gdms.consumer.blog.mapper"})
public class ConsumerBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerBlogApplication.class, args);
    }
}
