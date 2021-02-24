package cn.xlbweb.ji;

import cn.xlbweb.util.DateUtils;
import cn.xlbweb.util.spring.ServletUtils;
import cn.xlbweb.util.spring.SpringUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.security.Key;

/**
 * jwt工具类
 *
 * @author: wudibo
 * @since 1.0.0
 */
public class JwtUtils {

    private final static Log logger = LogFactory.getLog(JwtUtils.class);

    private static JiProperties jiProperties = SpringUtils.getBean(JiProperties.class);

    /**
     * 加密key
     */
    private static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * jwt加密
     *
     * @param subject
     * @return
     */
    public static String encrypt(String subject) {
        return Jwts.builder().setSubject(subject).setExpiration(DateUtils.plusMinutes(jiProperties.getTokenExpirationTime())).signWith(key).compact();
    }

    /**
     * jwt解密
     *
     * @param jws
     * @return
     */
    public static String decrypt(String jws) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws).getBody().getSubject();
    }

    /**
     * 根据token获取用户名
     *
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        String[] userInfos = StringUtils.split(decrypt(token), "-");
        return userInfos[0];
    }

    /**
     * 根据当前请求获取用户名
     *
     * @return
     */
    public static String getUsername() {
        String token = ServletUtils.getCurrentRequest().getHeader(jiProperties.getTokenName());
        String[] userInfos = StringUtils.split(decrypt(token), "-");
        return userInfos[0];
    }

    /**
     * 根据token获取角色名
     *
     * @param token
     * @return
     */
    public static String getRoleName(String token) {
        String[] userInfos = StringUtils.split(decrypt(token), "-");
        if (userInfos.length > 1) {
            return userInfos[1];
        }
        return StringUtils.EMPTY;
    }

    /**
     * 根据当前请求获取角色名
     *
     * @return
     */
    public static String getRoleName() {
        String token = ServletUtils.getCurrentRequest().getHeader(jiProperties.getTokenName());
        String[] userInfos = StringUtils.split(decrypt(token), "-");
        if (userInfos.length > 1) {
            return userInfos[1];
        }
        return StringUtils.EMPTY;
    }
}
