package lhb.gdms.configuration.security.handler;
import com.fasterxml.jackson.databind.ObjectMapper;

import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.constant.ResponseConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 自定义异常处理类
 * 校验token是否过期
 * 用来解决匿名用户访问无权限资源时的异常,也就是跟token相关的资源异常
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/7/23
 * @time 11:20
 */
@Configuration(value = "authExceptionEntryHandler")
public class AuthExceptionEntryHandler implements AuthenticationEntryPoint {

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws ServletException {


        Map<String, Object> map = new HashMap<String, Object>();
        Throwable cause = authException.getCause();
        if (cause instanceof InvalidTokenException) {
            log.error("token无效,InvalidTokenException : {}", cause.getMessage());
            map.put(ResponseConstant.CODE, HttpConstant.INVALID_TOKEN);//40
            map.put(ResponseConstant.MESSAGE, HttpConstant.INVALID_TOKEN_MESSAGE);
        } else {
            log.error("没有登录");
            map.put(ResponseConstant.CODE, HttpConstant.NOT_LOGIN);//401
            map.put(ResponseConstant.MESSAGE, HttpConstant.NOT_LOGIN_MESSAGE);
        }
        map.put("timestamp", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        try {
            ObjectMapper mapper = new ObjectMapper();
            log.debug(map.toString());
            mapper.writeValue(response.getOutputStream(), map);
        } catch (Exception e) {
            throw new ServletException();
        }
    }

//    /**
//     * token 验证
//     * @param request
//     * @param response
//     * @param authException
//     * @throws IOException
//     * @throws ServletException
//     */
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//
//        Throwable cause = authException.getCause();
//        response.setStatus(HttpServletResponse.SC_OK);
//        response.setContentType("application/json; charset=UTF-8");
//        // CORS "pre-flight" request
//        response.addHeader("Access-Control-Allow-Origin", "*");
//        response.addHeader("Cache-Control","no-cache");
//        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
//        response.addHeader("Access-Control-Max-Age", "1800");
//
//        ObjectMapper mapper = new ObjectMapper();
//        if (cause instanceof InvalidTokenException) {
//            log.error("token无效,InvalidTokenException : {}", cause.getMessage());
//            response.getWriter().write(mapper.writeValueAsString(BaseResult.error(HttpConstant.INVALID_TOKEN, HttpConstant.INVALID_TOKEN_MESSAGE)));
//        } else {
//            log.error("没有登录");
//            response.getWriter().write(mapper.writeValueAsString(BaseResult.error(HttpConstant.NOT_LOGIN,HttpConstant.NOT_LOGIN_MESSAGE)));
//        }
//
//    }
}
