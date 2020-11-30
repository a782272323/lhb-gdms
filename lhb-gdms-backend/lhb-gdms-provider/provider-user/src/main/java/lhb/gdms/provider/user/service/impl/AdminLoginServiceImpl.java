package lhb.gdms.provider.user.service.impl;

import com.google.common.collect.Maps;
import lhb.gdms.commons.constant.HttpConstant;
import lhb.gdms.commons.constant.ResponseConstant;
import lhb.gdms.commons.domain.dto.Oauth2DTO;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.feign.cloud.Oauth2Feign;
import lhb.gdms.provider.user.domain.vo.LoginParamVO;
import lhb.gdms.provider.user.service.AdminLoginService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description  
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/22 
 * @time 10:40
 */
@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Oauth2Feign oauth2Feign;

    @Autowired
    private RedisTokenStore tokenStore;

    /**
     * 管理网站登录校验
     * @param loginParamVO
     * @return
     */
    @Override
    public BaseResult adminLogin(LoginParamVO loginParamVO) {

        // 拼接参数，请求获取token
        Map<String, Object> params = Maps.newHashMap();
        params.put("grant_type", "password");
        params.put("username", loginParamVO.getUsername());
        params.put("password", loginParamVO.getPassword());
        params.put("scope", "web-admin");
        params.put("client_id", "admin");
        params.put("client_secret", "123456");

        BaseResult result = oauth2Feign.getToken(params);
        logger.debug("-----result-----: {}", result.toString());
        String access_token = result.get("access_token").toString();

        // todo 这个功能看情况，先不上线
        // 实现一个设备只能登录一个账户
        // 原理:换新的浏览器时，新浏览器是没有存储用户token的，故每次登录若浏览器没有token存储的相关信息
        // 可以可以认为token过期或者用户换新浏览器，故删除旧token再重新获取
//        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(access_token);
//        tokenStore.removeAccessToken(oAuth2AccessToken);
//        result = oauth2Feign.getToken(params);
//        logger.debug("-----result-----: {}", result.toString());
//        access_token = result.get("access_token").toString();

        // 封装登录信息给前端
        if (StringUtils.isNotBlank(access_token)) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("access_token", access_token);
            map.put("token_type", result.get("token_type"));
            map.put("refresh_token", result.get("refresh_token"));
            map.put("expires_in", result.get("expires_in"));
            map.put("scope", result.get("scope"));
            map.put("token", access_token);

            return BaseResult.ok().put(HttpConstant.OK, HttpConstant.LOGIN_OK_MESSAGE, ResponseConstant.DATA, map);
        }


        return BaseResult.error("登录失败");
    }
}
