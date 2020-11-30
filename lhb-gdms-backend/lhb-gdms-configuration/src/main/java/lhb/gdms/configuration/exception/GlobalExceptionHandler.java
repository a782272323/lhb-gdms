package lhb.gdms.configuration.exception;

import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.utils.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author Herbie Leung(梁鸿斌)
 * @Description 全局异常统一处理
 * @date 2020/11/25
 * @time 09:53
 */
@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 参数校验未通过异常 @RequestBody 参数校验失败
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public BaseResult bindExceptionHandler(Exception e) {
        e.printStackTrace();
        // 参数校验未通过异常 @RequestBody 参数校验失败
        List<ObjectError> errors = ((BindException) e).getBindingResult().getAllErrors();
        StringBuffer sb = new StringBuffer();
        for (ObjectError error : errors) {
            String message = error.getDefaultMessage();
            sb.append(message).append(";");
        }
        logger.error(sb.toString());
        return BaseResult.error(HttpConstant.ERROR, sb.toString());
    }

    /**
     * 系统异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BaseResult exceptionHandler(Exception e) {
        e.printStackTrace();
        logger.error(e.getMessage());
        return BaseResult.error(HttpConstant.ERROR, HttpConstant.ERROR_MESSAGE);
    }

    /**
     * 参数格式错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public BaseResult constraintViolationHandler(ConstraintViolationException e) {
        String errorMessage = e.getMessage().split("\\.")[1];
        logger.error(errorMessage);
        return BaseResult.error(HttpConstant.LOST_PARAMETER, HttpConstant.PARAMETER_FORMAT_ERROR_MESSAGE);
    }

    /**
     * 参数类型异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public BaseResult methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException e) {
        logger.error(HttpConstant.PARAMETER_TYPE_ERROR_MESSAGE);
        return BaseResult.error(HttpConstant.LOST_PARAMETER, HttpConstant.PARAMETER_TYPE_ERROR_MESSAGE);
    }

    /**
     * 参数缺失
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public BaseResult missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        logger.error(HttpConstant.LOST_PARAMETER_MESSAGE);
        return BaseResult.error(HttpConstant.LOST_PARAMETER, HttpConstant.LOST_PARAMETER_MESSAGE);
    }

    /**
     * 参数缺少。文件类型
     * @param e
     * @return
     */
    @ExceptionHandler(MultipartException.class)
    @ResponseBody
    public BaseResult multipartExceptionHandler(MultipartException e) {
        logger.error("该接口是文件缺口，缺少文件参数");
        return BaseResult.error(HttpConstant.LOST_PARAMETER,"缺少文件类型参数");
    }

    /**
     * 空指针异常
     * @param e
     * @return
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public BaseResult nullPointerExceptionHandler(NullPointerException e) {
        e.printStackTrace();
        logger.error(HttpConstant.NULL_POINTER_MESSAGE);
        return BaseResult.error(HttpConstant.ERROR, HttpConstant.ERROR_MESSAGE);
    }
}
