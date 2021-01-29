package cn.xlbweb.ji;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * @author: bobi
 * @date: 2020/9/8 下午10:42
 * @description:
 */
@Configuration
public class JiWebConfig implements WebMvcConfigurer {

    private final Log logger = LogFactory.getLog(JiWebConfig.class);

    @Autowired
    private JiInterceptor jiInterceptor;

    @Autowired
    private JiProperties jiProperties;

    @Bean
    public CorsFilter corsFilter() {
        logger.info("Init cors filter...");
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(3600L);
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("Init interceptor...");
        List<String> excludeUris = Arrays.asList(StringUtils.split(jiProperties.getExcludeUris(), ","));
        logger.info("exclude uris=" + excludeUris);
        registry.addInterceptor(jiInterceptor).addPathPatterns("/**").excludePathPatterns(excludeUris);
    }
}
