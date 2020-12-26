package lhb.gdms.commons.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日期工具类
 *
 * @author 梁鸿斌
 * @date 2020/3/7.
 * @time 00:16
 */
public class TimeUtils {

    /**
     * 日期格式
     */
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public final static String MM_DD = "MM-dd";
    public static String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public final static String YYYY_MM_DD_TIGHT = "yyyyMMdd";
    public final static String YYYY_MM_DD_HH_TIGHT = "yyyyMMddHH";
    public final static String YYYY_MM_DD_HH_MM_SS_TIGHT = "yyyyMMddHHmmss";
    public final static String YYYY_MM_TIGHT = "yyyyMM";
    public final static String YYYY_MM = "yyyy-MM";
    public final static String YYYY = "yyyy";
    public final static String CN_YYYY_MM_DD = "yyyy年MM月dd日";
    public static final String YYYY_MM_DD_AND_HH_MM_SS = "yyyy-MM-dd/HH:mm:ss";

    /**
     * 获取指定时间 （中国时区）
     * 自定义时间格式
     * @param dateFormat
     * @return
     */
    public static String baseDateTimeCN(String dateFormat) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.CANADA);
        String time = simpleDateFormat.format(date);
        return time;
    }

    /**
     * 获取指定时间
     * 自定义时间格式
     * @param dateFormat
     * @return
     */
    public static String baseDateTime(String dateFormat) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        String time = simpleDateFormat.format(date);
        return time;
    }

    /**
     * 获取当前时间 yyyy-MM-dd HH:mm:ss
     * 中国时区的 年月日 时分秒
     * @return
     */
    public static String getCurrentDateTime(){
        return baseDateTimeCN(YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取当前时间 yyyy-MM-dd
     * @return
     */
    public static String getCurrentDate(){
        return baseDateTime(YYYY_MM_DD);
    }

    /**
     * 获取当前时间 yyyyMMdd
     * @return
     */
    public static String getCurrentTightDate(){
        return baseDateTime(YYYY_MM_DD_TIGHT);
    }

    /**
     * 获取当前时间 yyyyMM
     * @return
     */
    public static String getCurrentMonth(){
        return baseDateTime(YYYY_MM_TIGHT);
    }

    public static Date getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }
}