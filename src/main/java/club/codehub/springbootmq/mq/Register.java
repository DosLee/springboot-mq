package club.codehub.springbootmq.mq;

import club.codehub.springbootmq.utils.SpringBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * 描述:
 * spring注册容器
 *
 * @author LIL on 2020-05-06 21:41
 */
@Slf4j
@Configuration
public class Register {

    public static <T> boolean registerBean(String beanName, T bean) {
        ConfigurableApplicationContext context = (ConfigurableApplicationContext) SpringBeanUtil.getApplicationContext();
        context.getBeanFactory().registerSingleton(beanName, bean);
        log.info("[Register注册实例] beanName: {} 到容器 {}", beanName, bean);
        return true;
    }
}
