package lhb.gdms.provider.oauth2.config;

import lhb.gdms.provider.oauth2.granter.EmailTokenGranter;
import lhb.gdms.provider.oauth2.granter.SmsTokenGranter;
import lhb.gdms.provider.oauth2.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Description  授权/认证服务器配置
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/19
 * @time 23:39
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthServiceConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 认证管理器
     */
    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private UserDetailsServiceImpl userDetailsService;

    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    @Resource
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 令牌存储在redis
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        //使用数据库来操作token
        return new RedisTokenStore(redisConnectionFactory);
    }

    /**
     * 从数据库读取clientDetails配置
     * @return
     */
    @Bean
    public ClientDetailsService clientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * 注入密码实现器
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * 从jdbc数据库读取clientDetails的配置
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .withClientDetails(clientDetailsService());



//        clients
//                // 基于内存存储
//                .inMemory()
//                .withClient("portal")
//                .secret(passwordEncoder.encode("123456"))
//                .resourceIds("provider-oauth2")
//                .authorizedGrantTypes("password","refresh_token")
//                .scopes("web-portal")
//                ;
    }

    /**
     * 配置哪些可以访问认证服务器
     * 认证服务器端点配置
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints
                // 如果需要使用refresh_token,则需要注入userDetailService
                // 用于支持密码模式
                .userDetailsService(userDetailsService)
                // 令牌存储策略，存储在redis
                .tokenStore(tokenStore())
                //authenticationManager校验传递进来的用户是否合法
                .authenticationManager(authenticationManager)
                ;
        // 添加自定义授权方式
        endpoints.tokenGranter(tokenGranter(endpoints));
    }

    /**
     * 认证服务器相关接口权限管理
     * 配置验证令牌的条件(即满足什么样的条件才能找我验证令牌 不是随便拿token也来验证)
     * 这里都先做一个最基本的配置
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

        security
                // 开启/oauth/token_key验证端口无权限访问
                .tokenKeyAccess("permitAll()")
                // 检验服务验证的规则 必须是经过验证的用户名,密码才给你验证令牌
                // 允许客户端访问 /oauth/check_token 检查 token
                .checkTokenAccess("isAuthenticated()")
                // 允许表单验证(前端),申请令牌
                .allowFormAuthenticationForClients()
        ;
    }

    /**
     * 获取已有的五种授权，然后添加自定义授权sms,email
     * todo 邮箱也在这里添加
     * @param endpoints
     * @return
     */
    private TokenGranter tokenGranter(final AuthorizationServerEndpointsConfigurer endpoints) {
        List<TokenGranter> granters = new ArrayList<>(Collections.singletonList(endpoints.getTokenGranter()));
        granters.add(new SmsTokenGranter(endpoints.getTokenServices(),
                endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory(),
                userDetailsService));
        granters.add(new EmailTokenGranter(endpoints.getTokenServices(),
                endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory(),
                userDetailsService));
        return new CompositeTokenGranter(granters);
    }
}
