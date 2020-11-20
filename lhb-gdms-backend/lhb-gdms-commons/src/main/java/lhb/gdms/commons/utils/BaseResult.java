package lhb.gdms.commons.utils;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * 通用数据传输对象
 * 返回结果集封装
 *
 * @author 梁鸿斌
 * @date 2020/3/6.
 * @time 23:58
 */
public class BaseResult extends HashMap<String,Object> {
    private static final long serialVersionUID = 4014025446804183746L;


    /**
     * HTTP 状态码
     */
    private int code;


    /**
     * 信息
     */
    private String message;
    /**
     * 封装实体类的map对象名（记得用双引号包裹）
     */
    private String key;
    /**
     * 封装实体类的map对象
     */
    private Object data;

    /**
     * 日期格式化
     * @return
     */
    private static String baseDateTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);

    }

    /** s
     * 返回 HTTP 和 消息
     * @param code
     * @param message
     * @return
     */
    private static BaseResult createResult(int code, String message) {
        BaseResult baseResult = new BaseResult();
        baseResult.put(Message.CODE, code);
        baseResult.put(Message.MESSAGE, message);
        baseResult.put(Message.TIMESTAMP, baseDateTime());
        return baseResult;
    }

    /**
     * 返回 HTTP状态码 和 消息 和 结果集
     *
     * @param code 状态码
     * @param message 消息
     * @param key   结果集的属性名（加双引号）
     * @param data 结果集对象
     * @return0
     */
    private static BaseResult createResult(int code,String message, String key,Object data) {
        BaseResult baseResult = new BaseResult();
        baseResult.put(Message.CODE,code);
        baseResult.put(Message.MESSAGE,message);
        baseResult.put(Message.TIMESTAMP, baseDateTime());
        baseResult.put(key, data);
        return baseResult;
    }

    /**
     * 链式编程，返回 HTTP状态码和消息还有 结果集
     *
     * @param key 返回给前端的json的对象名
     * @param data 返回给前端的json的实体类或者别的数据
     * @param code 状态码
     * @param message 消息
     * @return
     */
    public BaseResult put(int code, String message, String key, Object data) {
        BaseResult baseResult = new BaseResult();
        baseResult.put(Message.CODE, code);
        baseResult.put(Message.MESSAGE, message);
        baseResult.put(Message.TIMESTAMP, baseDateTime());
        baseResult.put(key, data);
        return baseResult;
    }

    /**
     * 返回默认的请求成功描述
     *
     * @return
     */
    public static BaseResult ok() {
        return BaseResult.createResult(CodeStatus.OK, Message.OK);
    }

    /**
     * 返回成功的自定义的HTTP状态码和默认的消息
     *
     * @param code
     * @return
     */
    public static BaseResult ok(int code) {
        return BaseResult.createResult(code, Message.OK);
    }

    /**
     * 返回成功的默认的HTTP状态码和自定义的消息
     *
     * @param message
     * @return
     */
    public static BaseResult ok(String message) {
        return BaseResult.createResult(CodeStatus.OK, message);
    }

    /**
     * 返回成功的自定义HTTP状态和消息
     *
     * @param code
     * @param message
     * @return
     */
    public static BaseResult ok(int code, String message) {
        return BaseResult.createResult(code, message);
    }

    /**
     * 返回默认的失败的 HTTP状态码 和 消息
     *
     * @return
     */
    public static BaseResult error() {
        return BaseResult.createResult(CodeStatus.ERROR, Message.ERROR);
    }

    /**
     * 返回自定义的HTTP状态码 和 默认的消息
     *
     * @param code
     * @return
     */
    public static BaseResult error(int code) {
        return BaseResult.createResult(code, Message.ERROR);
    }

    /**
     * 返回默认的HTTP状态码 和 默认的消息
     *
     * @param message
     * @return
     */
    public static BaseResult error(String message) {
        return BaseResult.createResult(CodeStatus.ERROR, message);
    }

    /**
     * 返回自定义的 HTTP状态码和消息
     *
     * @param code
     * @param message
     * @return
     */
    public static BaseResult error(int code, String message) {
        return BaseResult.createResult(code, message);
    }
    
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    // 内部类 状态码
    public class CodeStatus {
        /**
         * 成功
         */
        public static final int OK = 200;
        /***
         * 失败
         */
        public static final int ERROR = 500;
    }

    /**
     * 结果消息封装
     */
    public class Message{
        private static final String OK = "请求成功！";
        private static final String ERROR = "请求失败!";
        private static final String CODE = "code";
        private static final String MESSAGE = "message";
        private static final String TIMESTAMP = "timestamp";
    }
}