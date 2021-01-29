package cn.xlbweb.ji;

import cn.xlbweb.util.ServletUtils;
import cn.xlbweb.util.res.ResponseServer;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: bobi
 * @date: 2020/9/8 下午10:42
 * @description:
 */
@Component
public class JiInterceptor implements HandlerInterceptor {

    private final Log logger = LogFactory.getLog(JiInterceptor.class);

    @Autowired
    private JiProperties jiProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String uri = request.getRequestURI();

        String token = request.getHeader(jiProperties.getTokenName());
        if (StringUtils.isBlank(token)) {
            logger.error("拦截请求[" + uri + "],原因:Token不能为空");
            ServletUtils.printResponse(response, ResponseServer.error("Token不能为空"));
            return false;
        }

        // todo 过滤请求

        try {
            String userId = JwtUtils.jwtDecrypt(token);
            System.out.println("userId=" + userId);
            return true;
        } catch (ExpiredJwtException e) {
            logger.error("拦截请求[" + uri + "],原因:" + jiProperties.getTokenInvalidMessage(), e);
            ResponseServer responseServer = ResponseServer.error(jiProperties.getTokenInvalidCode(), jiProperties.getTokenInvalidMessage());
            ServletUtils.printResponse(response, responseServer);
        } catch (JwtException e) {
            logger.error("拦截请求[" + uri + "],原因:" + jiProperties.getTokenNonstandardMessage(), e);
            ResponseServer<Object> responseServer = ResponseServer.error(jiProperties.getTokenNonstandardCode(), jiProperties.getTokenNonstandardMessage());
            ServletUtils.printResponse(response, responseServer);
        }
        return false;
    }
}