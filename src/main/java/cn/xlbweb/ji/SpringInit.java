package cn.xlbweb.ji;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Spring组件初始化类
 *
 * @author: wudibo
 * @since 1.0.0
 */
@Configuration
@Import({JiInterceptor.class, JiProperties.class, JiWebConfig.class})
public class SpringInit {
}
