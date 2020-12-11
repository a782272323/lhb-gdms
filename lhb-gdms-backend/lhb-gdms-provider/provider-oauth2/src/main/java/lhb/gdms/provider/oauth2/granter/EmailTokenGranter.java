package lhb.gdms.provider.oauth2.granter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Map;
import java.util.Objects;

/**
 * @Description  自定义授权模式，邮箱验证授权
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/11
 * @time 14:30
 */
public class EmailTokenGranter extends AbstractTokenGranter {

    /**
     * 授权模式代码
     */
    private static final String GRANT_TYPE = "email";
    private UserDetailsService userDetailsService;

    /**
     * 构造方法提供一些必要的注入参数
     * 通过这些参数来完成父类的构建
     * @param tokenServices
     * @param clientDetailsService
     * @param oAuth2RequestFactory
     * @param userDetailsService
     */
    public EmailTokenGranter(AuthorizationServerTokenServices tokenServices,
                             ClientDetailsService clientDetailsService,
                             OAuth2RequestFactory oAuth2RequestFactory,
                             UserDetailsService userDetailsService) {
        super(tokenServices, clientDetailsService, oAuth2RequestFactory, GRANT_TYPE);
        this.userDetailsService = userDetailsService;
    }

    /**
     * 查询用户信息，用于构建用户的授权信息
     * @param client
     * @param tokenRequest
     * @return
     */
    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> params = tokenRequest.getRequestParameters();
        String email = params.getOrDefault("email", "");
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        if (Objects.isNull(userDetails)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        Authentication user = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities());
        return new OAuth2Authentication(tokenRequest.createOAuth2Request(client), user);
    }
}
