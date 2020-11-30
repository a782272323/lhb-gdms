package lhb.gdms.provider.gateway.interceptor;//package lhb.gdms.configuration.security.interceptor;
//
//
//        import feign.RequestInterceptor;
//        import feign.RequestTemplate;
//        import org.springframework.context.annotation.Configuration;
//        import org.springframework.security.core.Authentication;
//        import org.springframework.security.core.context.SecurityContext;
//        import org.springframework.security.core.context.SecurityContextHolder;
//        import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
//
///**
// * @Description  feign配置类
// * @author Herbie Leung(梁鸿斌)
// * @date 2020/8/12
// * @time 00:16
// */
//@Configuration
//public class OAuth2FeignAutoConfiguration implements RequestInterceptor {
//
//    private final String AUTHORIZATION_HEADER = "Authorization";
//    private final String BEARER_TOKEN_TYPE = "Bearer ";
//
//    @Override
//    public void apply(RequestTemplate requestTemplate) {
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        Authentication authentication = securityContext.getAuthentication();
//        if (authentication != null && authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
//            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
//            requestTemplate.header(AUTHORIZATION_HEADER, String.format("%s %s", BEARER_TOKEN_TYPE, details.getTokenValue()));
//        }
//
//    }
//
//}