package lhb.gdms.configuration.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;

import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.utils.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description 自定义异常处理类
 * 校验token携带的权限是否足以访问接口资源
 * 用来解决认证过的用户访问无权限资源时的异常，主要跟权限控制相关
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/7/23
 * @time 11:20
 */
@Configuration(value = "customAccessDeniedHandler")
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 访问失败/访问被拒绝 的处理
     * @param request
     * @param response
     * @param accessDeniedException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
//        response.addHeader("Access-Control-Allow-Origin", "*");
//        response.addHeader("Cache-Control","no-cache");
//        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
//        response.addHeader("Access-Control-Max-Age", "1800");
        ObjectMapper mapper = new ObjectMapper();
        log.error("权限不足");
        response.getWriter().write(mapper.writeValueAsString(BaseResult.error(HttpConstant.ACCESS_DENIED,HttpConstant.ACCESS_DENIED_MESSAGE)));
    }
}
