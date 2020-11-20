package lhb.gdms.commons.constant;

/**
 * @Description  状态码和响应信息的常量类
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/8/3
 * @time 00:57
 */
public class HttpConstant {

    /**
     * 200
     * 请求成功
     * 上传成功
     */
    public static final Integer OK = 200;
    public static final String OK_MESSAGE = "请求成功";
    public static final String UPLOAD_MESSAGE = "上传成功";

    /**
     * 201
     * 保存成功
     */
    public static final Integer SAVE = 201;
    public static final String SAVE_MESSAGE = "保存成功";

    /**
     * 202
     * 编辑成功
     */
    public static final Integer EDIT = 202;
    public static final String EDIT_MESSAGE = "编辑成功";

    /**
     * 203
     * 删除成功
     */
    public static final Integer DELETE = 203;
    public static final String DELETE_MESSAGE = "删除成功";


    /**
     * 401
     * 访问此资源需要完全的身份验证,请登录系统
     */
    public static final Integer NOT_LOGIN = 401;
    public static final String NOT_LOGIN_MESSAGE = "访问此资源需要完全的身份验证,请登录系统";

    /**
     * 402
     * 登录过期，请重新登录
     */
    public static final Integer INVALID_TOKEN = 411;
    public static final String INVALID_TOKEN_MESSAGE = "登录过期，请重新登录";

    /**
     * 403
     * 该用户权限不足以访问该资源接口
     */
    public static final Integer ACCESS_DENIED = 403;
    public static final String ACCESS_DENIED_MESSAGE = "该用户权限不足以访问该资源接口";

    /**
     * 404
     * 非法token/token不正确
     */
    public static final Integer ILLEGAL_TOKEN = 404;
    public static final String ILLEGAL_TOKEN_MESSAGE = "非法token/token不正确";

    /**
     * 405
     * 其他设备已经登录,是否挤下线
     */
    public static final Integer HAVE_LOGIN = 405;
    public static final String HAVE_LOGIN_MESSAGE = "其他设备已经登录,是否挤下线";

    /**
     * 410
     * 保存失败
     */
    public static final Integer SAVE_ERROR = 410;
    public static final String SAVE_ERROR_MESSAGE = "保存失败";

    /**
     * 411
     * 编辑失败
     */
    public static final Integer EDIT_ERROR = 411;
    public static final String EDIT_ERROR_MESSAGE = "编辑失败";

    /**
     * 412
     * 删除失败
     */
    public static final Integer DELETE_ERROR = 412;
    public static final String DELETE_ERROR_MESSAGE = "删除失败";

    /**
     * 500
     * 服务器内部异常，请联系管理员
     * 上传失败
     */
    public static final Integer ERROR = 500;
    public static final String ERROR_MESSAGE = "服务器内部异常，请联系管理员";
    public static final String UPLOAD_ERROR_MESSAGE = "上传失败";

    /**
     * 501
     * 该接口到达阀值(上限)了，请稍后重试
     */
    public static final Integer MAX_REQUEST = 501;
    public static final String MAX_REQUEST_MESSAGE = "该接口到达阀值(上限)了，请稍后重试";
}
