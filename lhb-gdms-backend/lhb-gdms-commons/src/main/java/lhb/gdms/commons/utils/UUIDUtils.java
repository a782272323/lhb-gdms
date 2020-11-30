package lhb.gdms.commons.utils;

import java.util.UUID;

/**
 * @Description  UUID 工具类
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/24
 * @time 19:44
 */
public class UUIDUtils {

    /**
     * 生成指定位数的UUID
     * @param number
     * @return
     */
    public static String[] getUUID(int number) {
        if (number < 1) {
            return null;
        }
        String[] UUIDString = new String[number];
        for (int i = 0; i < number; i++) {
            UUIDString[i] = getUUID();
        }
        return UUIDString;
    }

    /**
     * 生成一个UUID
     * @return
     */
    public static String getUUID() {
        String uuidString = UUID.randomUUID().toString();
        // 去掉 -
        return uuidString.replaceAll("-", "");
    }
}
