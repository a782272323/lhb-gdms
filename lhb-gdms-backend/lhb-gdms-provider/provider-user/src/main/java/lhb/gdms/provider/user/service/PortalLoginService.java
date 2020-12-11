package lhb.gdms.provider.user.service;

import lhb.gdms.commons.domain.entity.SysUserEntity;
import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.provider.user.domain.vo.LoginPortalParamVO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/24
 * @time 19:49
 */
public interface PortalLoginService {

    /**
     * 注册
     * @param sysUserEntity
     * @return
     */
    public BaseResult portalRegistered(SysUserEntity sysUserEntity);

    /**
     * 门户网站用户登录
     * @param loginPortalParamVO
     * @return
     */
    public BaseResult portalLogin(LoginPortalParamVO loginPortalParamVO);

}
