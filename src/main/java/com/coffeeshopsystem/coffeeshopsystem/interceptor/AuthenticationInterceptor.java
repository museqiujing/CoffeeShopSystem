package com.coffeeshopsystem.coffeeshopsystem.interceptor;

import com.coffeeshopsystem.coffeeshopsystem.exception.AuthenticationException;
import com.coffeeshopsystem.coffeeshopsystem.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果是OPTIONS请求，直接放行
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        // 从请求头中获取token
        String token = extractToken(request);

        if (!StringUtils.hasText(token)) {
            handleAuthenticationError(response, "未提供认证令牌");
            return false;
        }

        // 验证token
        try {
            if (!jwtUtil.validateToken(token)) {
                handleAuthenticationError(response, "无效的认证令牌");
                return false;
            }

            // 获取token中的用户信息
            Claims claims = jwtUtil.getClaimsFromToken(token);

            // 将用户信息添加到请求属性中
            request.setAttribute("userId", claims.get("userId"));
            request.setAttribute("username", claims.get("username"));
            request.setAttribute("role", claims.get("role"));

            return true;
        } catch (Exception e) {
            handleAuthenticationError(response, "认证处理失败");
            return false;
        }
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private void handleAuthenticationError(HttpServletResponse response, String message) throws Exception {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 401);
        result.put("message", message);

        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}