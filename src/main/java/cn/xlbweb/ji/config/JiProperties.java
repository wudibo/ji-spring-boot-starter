package cn.xlbweb.ji.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author: bobi
 * @date: 2020/9/8 下午10:42
 * @description:
 */
@Configuration
public class JiProperties {

    @Value("${cn.xlbweb.ji.exclude-uris:/**}")
    private String excludeUris;

    @Value("${cn.xlbweb.ji.token-name:JiToken}")
    private String tokenName;

    @Value("${cn.xlbweb.ji.expiration-time:60}")
    private Long expirationTime;

    /** getter and setter method */

    public String getExcludeUris() {
        return excludeUris;
    }

    public void setExcludeUris(String excludeUris) {
        this.excludeUris = excludeUris;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public Long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Long expirationTime) {
        this.expirationTime = expirationTime;
    }
}
