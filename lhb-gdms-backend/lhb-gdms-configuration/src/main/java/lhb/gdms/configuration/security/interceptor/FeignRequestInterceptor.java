//package lhb.gdms.configuration.security.interceptor;
//
//import feign.Request;
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.nio.charset.Charset;
//import java.util.Enumeration;
//
///**
// * 配置 Feign 拦截器.
// * 用于设置请求头，传递 Token
// *
// * @author 梁鸿斌
// * @date 2020/3/8.
// * @time 15:04
// */
//public class FeignRequestInterceptor implements RequestInterceptor {
//
//    /**
//     * 重新拼接请求头给feign
//     * @param template 请求体
//     */
//    @Override
//    public void apply(RequestTemplate template) {
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        // 断言attributes 不为空
//        assert attributes != null;
//        HttpServletRequest request = attributes.getRequest();
//        // 设置请求头
//        // todo Enumeration
//        Enumeration<String> headerNames = request.getHeaderNames();
//        if (headerNames != null) {
//            while (headerNames.hasMoreElements()) {
//                // 获取token属性名
//                String name = headerNames.nextElement();
//                // 获取token值
//                String value = request.getHeader(name);
//                template.header(name, value);
//            }
//        }
//
//        // 设置请求体，这里是传递access_token
//        Enumeration<String> parameterNames = request.getParameterNames();
//
//        StringBuilder body = new StringBuilder();
//        if (parameterNames != null) {
//            // 遍历获取 access_token
//            while (parameterNames.hasMoreElements()) {
//                // 获取token的属性名
//                String name = parameterNames.nextElement();
//                // 获取token的值
//                String value = request.getParameter(name);
//                // 如果是access_token的话，把令牌token放入header，否则放入body
//                if ("access_token".equals(name)) {
//                    template.header("authorization", "Bearer " + value);
//                }
//                // 其他的参数加入请求头
//                else {
//                    body.append(name).append("=").append(value).append("&");
//                }
//            }
//        }
//
//        // 设置请求体
//        if (body.length() > 0) {
//            // 去掉最后的符号 &
//            body.deleteCharAt(body.length() - 1);
//
//            template.body(Request.Body.bodyTemplate(body.toString(), Charset.defaultCharset()));
//        }
//    }
//}
