package lhb.gdms.provider.cloud;

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
 * @time 16:33
 */
@SpringBootApplication
@ComponentScan(basePackages = {"lhb.gdms"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"lhb.gdms.feign.*"})
@MapperScan(basePackages = {"lhb.gdms.provider.cloud.mapper"})
public class ProviderCloudApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderCloudApplication.class, args);
    }
}
