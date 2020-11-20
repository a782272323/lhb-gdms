package lhb.gdms.commons.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @Description  字符串变形工具类
 * @author Herbie Leung
 * @date 2020/7/1 
 * @time 09:40
 */
public class StringChangeUtils {


    /**
     * 实现 10000(整型) 去掉首字母1转成 0000 字符串
     * 也可以实现 100，1000等等，反正把1去掉，变成00的字符串
     * @param string
     * @param start
     * @return
     */
    public static String subStr1(String string, int start) {
        if (StringUtils.isBlank(string) || string.length() == 0) {
            return "";
        }
        if (start < string.length()) {
            return string.substring(start);
        } else {
            return "";
        }
    }
    
    
}