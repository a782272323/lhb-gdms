package lhb.gdms.provider.user.service;

import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.provider.user.domain.vo.LoginParamVO;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/22
 * @time 11:03
 */
public interface AdminLoginService {

    /**
     * 管理网站登录校验
     * @param loginParamVO
     * @return
     */
    BaseResult adminLogin(LoginParamVO loginParamVO);
}
