package lhb.gdms.consumer.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/24
 * @time 08:50
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"lhb.gdms"})
@EnableFeignClients(basePackages = {"lhb.gdms.feign.*"})
@MapperScan(basePackages = {"lhb.gdms.consumer.admin.mapper"})
public class ConsumerAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerAdminApplication.class, args);
    }
}
