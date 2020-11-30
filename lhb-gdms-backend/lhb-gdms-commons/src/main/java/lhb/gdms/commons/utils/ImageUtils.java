package lhb.gdms.commons.utils;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Description  图片处理工具类
 * @author Herbie Leung
 * @date 2020/7/1
 * @time 14:43
 */
public class ImageUtils {

    // 图片格式 小写
    public static String IMAGE_TYPE_GIF = "gif";
    public static String IMAGE_TYPE_JPG = "jpg";
    public static String IMAGE_TYPE_PNG = "png";
    public static String IMAGE_TYPE_PSD = "psd";
    public static String IMAGE_TYPE_BMP = "bmp";
    public static String IMAGE_TYPE_JPEG = "jpeg";
    // 图片格式 大写
    public static String IMAGE_TYPE_GIF_CAPITAL = "GIF";
    public static String IMAGE_TYPE_JPG_CAPITAL = "JPG";
    public static String IMAGE_TYPE_PNG_CAPITAL = "PNG";
    public static String IMAGE_TYPE_PSD_CAPITAL = "PSD";
    public static String IMAGE_TYPE_BMP_CAPITAL = "BMP";
    public static String IMAGE_TYPE_JPEG_CAPITAL = "JPEG";

    /**
     * 限制图片最大容量(以M为单位)
     * 1M = 1 * 1024 * 1024 依次类推
     * @param size
     * @return
     */
    public static String imgSizeLimitMax(Integer size, MultipartFile file) {
        if (file.getSize() > size * 1024 * 1024) {
            return "图片大小不能超过" + size + "M";
        } else {
            return "图片正常";
        }
    }

    /**
     * 校验图片上传格式
     * @param file 图片文件
     * @param imgFormat 图片后缀名字,例如png,jpg等等
     * @return
     */
    public static String isImgFormat(MultipartFile file,String imgFormat) {
        // 检测文件名是否有误
        String[] imgArray = file.getOriginalFilename().split("\\.");
        if (imgArray.length != 2) {
            return "文件名不正确";
        } else {
            if (imgArray[1].toString().trim().equals(imgFormat.trim())) {
                return "图片正常";
            } else {
                return "图片不正常";
            }
        }
    }

}