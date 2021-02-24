package cn.xlbweb.ji;

import cn.xlbweb.util.response.ServerResponse;
import cn.xlbweb.util.spring.ServletUtils;
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
import java.util.Objects;

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
        String token = request.getHeader(jiProperties.getTokenName());
        String uri = request.getRequestURI();
        // 校验token是否为空
        if (StringUtils.isBlank(token)) {
            logger.error("拦截请求[" + uri + "]Token不能为空");
            ServletUtils.outputJson(response, ServerResponse.error("Token不能为空"));
            return false;
        }

        // 校验token的有效性
        try {
            JwtUtils.decrypt(token);
        } catch (ExpiredJwtException e) {
            logger.error("拦截请求[" + uri + "]" + tokenInvalidMessage, e);
            ServerResponse serverResponse = ServerResponse.error(jiProperties.getTokenInvalidCode(), tokenInvalidMessage);
            ServletUtils.outputJson(response, serverResponse);
            return false;
        } catch (JwtException e) {
            logger.error("拦截请求[" + uri + "]" + tokenNonstandardMessage, e);
            ServerResponse serverResponse = ServerResponse.error(jiProperties.getTokenNonstandardCode(), tokenNonstandardMessage);
            ServletUtils.outputJson(response, serverResponse);
            return false;
        }

        // 校验注解角色
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequiresAdmin requiresAdmin = handlerMethod.getMethod().getDeclaredAnnotation(RequiresAdmin.class);
        if (Objects.nonNull(requiresAdmin)) {
            if (StringUtils.equalsIgnoreCase(JwtUtils.getRoleName(token), JiConstant.ADMIN)) {
                return true;
            } else {
                logger.error("非超级管理员，无权操作");
                ServletUtils.outputJson(response, "无权操作");
                return false;
            }
        }

        RequiresManager requiresManager = handlerMethod.getMethod().getDeclaredAnnotation(RequiresManager.class);
        if (Objects.nonNull(requiresManager)) {
            if (StringUtils.equalsIgnoreCase(JwtUtils.getRoleName(token), JiConstant.ADMIN) || StringUtils.equalsIgnoreCase(JwtUtils.getRoleName(token), JiConstant.MANAGER)) {
                return true;
            } else {
                logger.error("非普通管理员，无权操作");
                ServletUtils.outputJson(response, "无权操作");
                return false;
            }
        }
        return true;
    }
}
