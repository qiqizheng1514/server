package org.example.server.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 请求日志拦截器
 */
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 记录请求开始时间
        request.setAttribute("startTime", System.currentTimeMillis());
        
        // 记录请求信息
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        logger.info("Request received - URI: {}, Method: {}", requestURI, method);
        
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        // 请求处理完成后记录日志
        Long startTime = (Long) request.getAttribute("startTime");
        if (startTime != null) {
            long executionTime = System.currentTimeMillis() - startTime;
            String requestURI = request.getRequestURI();
            int status = response.getStatus();
            
            logger.info("Request completed - URI: {}, Status: {}, Time: {}ms", requestURI, status, executionTime);
        }
    }
} 