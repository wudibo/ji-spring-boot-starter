package cn.xlbweb.ji;

import cn.xlbweb.util.DateUtils;
import cn.xlbweb.util.SpringUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.security.Key;

/**
 * @author: bobi
 * @date: 2020/9/8 下午10:42
 * @description:
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
    public static String jwtEncrypt(String subject) {
        return Jwts.builder().setSubject(subject).setExpiration(DateUtils.plusMinutes(jiProperties.getExpirationTime())).signWith(key).compact();
    }

    /**
     * jwt解密
     *
     * @param jws
     * @return
     */
    public static String jwtDecrypt(String jws) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws).getBody().getSubject();
    }
}
