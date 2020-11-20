package lhb.gdms.provider.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description  启动类
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/20
 * @time 16:33
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"lhb.gdms"})
public class ProviderCloudApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderCloudApplication.class, args);
    }
}
