package lhb.gdms.provider.user.dubbo;

import lhb.gdms.commons.utils.BaseResult;
import lhb.gdms.dubbo.user.UserDubbo;
import org.apache.dubbo.config.annotation.Service;

@Service(version = "1.0.0")
public class UserDubboImpl implements UserDubbo {



    @Override
    public BaseResult echo(String string) {
        return BaseResult.ok("dubbo远程测试成功");
    }
}
