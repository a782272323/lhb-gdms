package lhb.gdms.configuration.aop.config;

import java.lang.annotation.*;


/**
 * @Description  自定义注解
 * 作用：用面向切面的方法来打印传入参数和传出参数的日志
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/24
 * @time 00:23
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface PrintlnLog {

    /**
     * 自定义日志描述信息文案
     * @return
     */
    String description() default "";
}
