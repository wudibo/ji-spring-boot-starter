package cn.xlbweb.ji;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author: bobi
 * @date: 2020/9/8 下午10:42
 * @description:
 */
@Configuration
@Import({JiInterceptor.class, JiProperties.class, JiWebConfig.class})
public class SpringInit {
}
