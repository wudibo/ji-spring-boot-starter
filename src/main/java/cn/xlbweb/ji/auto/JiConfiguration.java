package cn.xlbweb.ji.auto;

import cn.xlbweb.ji.config.JiInterceptor;
import cn.xlbweb.ji.config.JiProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author: bobi
 * @date: 2020/9/8 下午10:42
 * @description:
 */
@Configuration
@Import({JiInterceptor.class, JiProperties.class})
public class JiConfiguration {
}
