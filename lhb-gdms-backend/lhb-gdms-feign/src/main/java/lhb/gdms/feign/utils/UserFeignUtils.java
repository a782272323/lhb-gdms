package lhb.gdms.feign.utils;

import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.constant.ResponseConstant;
import lhb.gdms.commons.domain.entity.SysUserEntity;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.commons.utils.MapperUtils;
import lhb.gdms.feign.cloud.QiniuFeign;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description  用户服务远程调用工具类
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/19
 * @time 01:45
 */
@Component
public class UserFeignUtils {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 查询用户数据
     * @param baseResult
     * @return
     * @throws Exception
     */
    public SysUserEntity getSysUserInfo(BaseResult baseResult) throws Exception{
        Map<String, Object> map = (Map<String, Object>) baseResult.get(ResponseConstant.DATA);
        String json = MapperUtils.mapToJson((Map) map.get(ResponseConstant.GET_LISTS));
        SysUserEntity sysUserEntity = MapperUtils.json2pojo(json, SysUserEntity.class);
        return sysUserEntity;
    }

    /**
     * 判断该用户修改头像时，之前是否上传了旧头像
     * @param key
     * @return
     */
    public boolean deleteOldUserIconKey(String key, QiniuFeign qiniuFeign) throws Exception{
        if (StringUtils.isNotBlank(key)) {
            // 删除七牛云上的key
            logger.debug("删除七牛云的key = " + key);
            BaseResult deleteResult = qiniuFeign.deleteOne(key);
            if (deleteResult.get("code").toString().equals("200") == false) {
                return false;
            }
        }
        return true;
    }
}
