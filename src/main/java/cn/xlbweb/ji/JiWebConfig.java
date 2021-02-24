package cn.xlbweb.ji;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * WebMvc配置
 *
 * @author: wudibo
 * @since 1.0.0
 */
@Configuration
public class JiWebConfig implements WebMvcConfigurer {

    private final Log logger = LogFactory.getLog(JiWebConfig.class);

    @Autowired
    private JiInterceptor jiInterceptor;

    @Autowired
    private JiProperties jiProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (!jiProperties.getEnabled()) {
            return;
        }
        logger.info("Init...[interceptors]");
        List<String> excludeUris = Arrays.asList(StringUtils.split(jiProperties.getExcludeUris(), ","));
        logger.info("Exclude uris=" + excludeUris);
        registry.addInterceptor(jiInterceptor).addPathPatterns("/**").excludePathPatterns(excludeUris);
    }
}
