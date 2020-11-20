package lhb.gdms.dubbo.user;

import lhb.gdms.commons.utils.BaseResult;

/**
 * @Description  用户服务远程dubbo调用api接口
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/19
 * @time 22:03
 */
public interface UserDubbo {

    /**
     * 测试dubbo远程调用
     * @param string
     * @return
     */
    BaseResult echo(String string);
}
