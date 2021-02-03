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
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * jwt拦截器
 *
 * @author: wudibo
 * @since 1.0.0
 */
@Component
public class JiInterceptor implements HandlerInterceptor {

    private final Log logger = LogFactory.getLog(JiInterceptor.class);

    private final String tokenInvalidMessage = "用户登陆超时";
    private final String tokenNonstandardMessage = "Token验证失败";

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


        try {
            String userId = JwtUtils.jwtDecrypt(token);
            // todo 根据 userId 查询该用户的角色
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RequiresAdmin requiresAdmin = handlerMethod.getMethod().getDeclaredAnnotation(RequiresAdmin.class);
            if (requiresAdmin != null) {
                String role = "";
                if (StringUtils.equals(role, "admin")) {
                    return true;
                }
                return false;
            }
        } catch (ExpiredJwtException e) {
            logger.error("拦截请求[" + uri + "],原因:" + tokenInvalidMessage, e);
            ResponseServer responseServer = ResponseServer.error(jiProperties.getTokenInvalidCode(), tokenInvalidMessage);
            ServletUtils.printResponse(response, responseServer);
        } catch (JwtException e) {
            logger.error("拦截请求[" + uri + "],原因:" + tokenNonstandardMessage, e);
            ResponseServer<Object> responseServer = ResponseServer.error(jiProperties.getTokenNonstandardCode(), tokenNonstandardMessage);
            ServletUtils.printResponse(response, responseServer);
        }
        return false;
    }
}
