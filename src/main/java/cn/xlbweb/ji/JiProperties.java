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

    @Value("${cn.xlbweb.ji.exclude-uris:/**}")
    private String excludeUris;

    @Value("${cn.xlbweb.ji.token-name:JiToken}")
    private String tokenName;

    @Value("${cn.xlbweb.ji.expiration-time:60}")
    private Long expirationTime;

    @Value("${cn.xlbweb.ji.token-invalid-code:-2}")
    private Integer tokenInvalidCode;

    @Value("${cn.xlbweb.ji.token-invalid-message:用户失效}")
    private String tokenInvalidMessage;

    @Value("${cn.xlbweb.ji.token-nonstandard-code:-3}")
    private Integer tokenNonstandardCode;

    @Value("${cn.xlbweb.ji.token-nonstandard-message:Token不标准}")
    private String tokenNonstandardMessage;

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

    public Integer getTokenInvalidCode() {
        return tokenInvalidCode;
    }

    public void setTokenInvalidCode(Integer tokenInvalidCode) {
        this.tokenInvalidCode = tokenInvalidCode;
    }

    public String getTokenInvalidMessage() {
        return tokenInvalidMessage;
    }

    public void setTokenInvalidMessage(String tokenInvalidMessage) {
        this.tokenInvalidMessage = tokenInvalidMessage;
    }

    public Integer getTokenNonstandardCode() {
        return tokenNonstandardCode;
    }

    public void setTokenNonstandardCode(Integer tokenNonstandardCode) {
        this.tokenNonstandardCode = tokenNonstandardCode;
    }

    public String getTokenNonstandardMessage() {
        return tokenNonstandardMessage;
    }

    public void setTokenNonstandardMessage(String tokenNonstandardMessage) {
        this.tokenNonstandardMessage = tokenNonstandardMessage;
    }
}
