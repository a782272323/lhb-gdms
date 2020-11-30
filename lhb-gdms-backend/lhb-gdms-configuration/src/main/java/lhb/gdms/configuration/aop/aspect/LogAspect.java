package lhb.gdms.configuration.aop.aspect;

import com.alibaba.fastjson.JSON;
import lhb.gdms.configuration.aop.config.PrintlnLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @Description  日志打印切面类
 * 注意：涉及到文件上传下载不易使用，会导致系统报错。todo 有空修复
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/24
 * @time 00:14
 */
@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 行分隔符
      */
    private static final String LINE_SEPARATOR = System.lineSeparator();

    /**
     * 自定义注解 @PrintLog 作为切面入口
     */
    @Pointcut("@annotation(lhb.gdms.configuration.aop.config.PrintlnLog)")
    public void PrintLog() {

    }

    /**
     * 切面方法
     * 传入的参数日志打印
     * @param joinPoint
     * @throws Throwable
     */
    @Before("PrintLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 方法开始
        String methodDetailDescription = this.getAspectMethodDescJP(joinPoint);
        logger.info("------------------------------- 日志开始 --------------------------");
        // 打印自定义方法描述
        logger.info("方法详情描述 : " + methodDetailDescription);
        // 打印请求传入参数
        logger.info("请求参数 : " + JSON.toJSONString(joinPoint.getArgs()));
        // 打印请求的 url
        logger.info("请求地址 : " + request.getRequestURI().toString());
        // 打印调用方法全路径以及执行方法
        logger.info("请求的类和方法 = 类 (" + joinPoint.getSignature().getDeclaringTypeName() + "), 方法 (" + joinPoint.getSignature().getName() + ")");
    }



    /**
     * 切面方法返回结果日志打印
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("PrintLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String aspectMethodLogDescPJ = getAspectMethodLogDescPJ(proceedingJoinPoint);
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        // 输出结果
        logger.info(aspectMethodLogDescPJ + ", 输出结果 : " + JSON.toJSONString(result));

        // 方法执行耗时
        logger.info("方法耗时 : " + (System.currentTimeMillis() - startTime) + " ms");

        return result;
    }

    /**
     * 切面方法执行完成后执行
     * @param joinPoint
     */
    @After("PrintLog()")
    public void doAfter(JoinPoint joinPoint) {
        logger.info("------------------------------- 日志结束 --------------------------" + LINE_SEPARATOR);
    }

    /**
     * 注解作用的切面方法的详细信息
     * @param joinPoint
     * @return
     * @throws Exception
     */
    public String getAspectMethodDescJP(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        return getAspectMethodLogDesc(targetName, methodName, arguments);
    }

    /**
     * 注解作用的切面方法的详细信息
     * @param proceedingJoinPoint
     * @return
     * @throws Exception
     */
    public String getAspectMethodLogDescPJ(ProceedingJoinPoint proceedingJoinPoint) throws Exception {
        String targetName = proceedingJoinPoint.getTarget().getClass().getName();
        String methodName = proceedingJoinPoint.getSignature().getName();
        Object[] arguments = proceedingJoinPoint.getArgs();
        return getAspectMethodLogDesc(targetName, methodName, arguments);
    }

    /**
     * 自定义注解的参数
     * @param targetName
     * @param methodName
     * @param arguments
     * @return
     * @throws Exception
     */
    public String getAspectMethodLogDesc(String targetName, String methodName, Object[] arguments) throws Exception {
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        StringBuffer description = new StringBuffer("");
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                description.append(method.getAnnotation(PrintlnLog.class).description());
                break;
            }
        }
        return description.toString();
    }
}
