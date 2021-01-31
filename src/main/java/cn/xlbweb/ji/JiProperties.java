package cn.xlbweb.ji;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author: bobi
 * @date: 2020/9/8 下午10:42
 * @description:
 */
@Configuration
public class JiProperties {

    @Value("${cn.xlbweb.ji.enabled:false}")
    private Boolean enabled;

    @Value("${cn.xlbweb.ji.exclude-uris:/**}")
    private String excludeUris;

    @Value("${cn.xlbweb.ji.token-name:JiToken}")
    private String tokenName;

    @Value("${cn.xlbweb.ji.token-expiration-time:60}")
    private Long tokenExpirationTime;

    @Value("${cn.xlbweb.ji.token-invalid-code:-2}")
    private Integer tokenInvalidCode;

    @Value("${cn.xlbweb.ji.token-nonstandard-code:-3}")
    private Integer tokenNonstandardCode;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

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

    public Long getTokenExpirationTime() {
        return tokenExpirationTime;
    }

    public void setTokenExpirationTime(Long tokenExpirationTime) {
        this.tokenExpirationTime = tokenExpirationTime;
    }

    public Integer getTokenInvalidCode() {
        return tokenInvalidCode;
    }

    public void setTokenInvalidCode(Integer tokenInvalidCode) {
        this.tokenInvalidCode = tokenInvalidCode;
    }

    public Integer getTokenNonstandardCode() {
        return tokenNonstandardCode;
    }

    public void setTokenNonstandardCode(Integer tokenNonstandardCode) {
        this.tokenNonstandardCode = tokenNonstandardCode;
    }
}
