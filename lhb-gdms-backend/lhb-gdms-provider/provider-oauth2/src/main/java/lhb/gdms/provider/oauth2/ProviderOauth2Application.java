package lhb.gdms.provider.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/19
 * @time 12:58
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"lhb.gdms"})
public class ProviderOauth2Application {

    public static void main(String[] args) {
        SpringApplication.run(ProviderOauth2Application.class, args);
    }
}
