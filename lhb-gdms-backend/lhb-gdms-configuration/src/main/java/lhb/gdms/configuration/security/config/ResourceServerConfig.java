package lhb.gdms.configuration.security.config;

import lhb.gdms.configuration.security.handler.AuthExceptionEntryHandler;
import lhb.gdms.configuration.security.handler.CustomAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;


@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    /**
     * 资源服务的ID，在单体写死，在微服务中根据服务来动态注入
     */
    @Value("${spring.application.name}")
    private String RESOURCE_ID;

    @Autowired
    private OAuth2WebSecurityExpressionHandler expressionHandler;

    @Autowired
    private AuthExceptionEntryHandler authExceptionEntryHandler;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
        OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                // 需要具体权限的接口
                // 仅门户网站能访问的
                // 仅后台管理网站能访问的
                .antMatchers("/admin/system/icon/**").hasAnyAuthority("System Admin")
                .antMatchers("/admin/system/label/**").hasAnyAuthority("System Admin")
                .antMatchers("/user/info/admin").hasAnyAuthority("System Admin")
                // 不需要权限的接口/放行的接口
                .antMatchers("/web/**").permitAll()
                .antMatchers("/user/login/**").permitAll()
                .antMatchers("/user/registered/**").permitAll()
                .and()
                // 统一自定义异常
                .exceptionHandling()
                .and()
                .csrf().disable();

        // 所有请求都需要token验证
        http.authorizeRequests().anyRequest().authenticated();
    }


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        System.out.println("RESOURCE_ID = " + RESOURCE_ID);
        resources
                .expressionHandler(expressionHandler)
                // 资源id
                .resourceId(RESOURCE_ID)
                .authenticationEntryPoint(authExceptionEntryHandler)
                .accessDeniedHandler(customAccessDeniedHandler)
        ;

    }
}
