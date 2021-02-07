package lhb.gdms.commons.base.config;


import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description  校验系统是否链接了数据库
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/21
 * @time 08:58
 */
@Configuration
public class MybatisConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return new ConfigurationCustomizer() {
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                logger.debug("MySQL数据库检查返回结果:Mybatis连接成功");
                configuration.setObjectWrapperFactory(new MapWrapperFactory());
            }
        };
    }
}