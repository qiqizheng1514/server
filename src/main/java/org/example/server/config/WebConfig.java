package org.example.server.config;

import org.example.server.interceptor.AuthInterceptor;
import org.example.server.interceptor.LoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoggingInterceptor loggingInterceptor;
    private final AuthInterceptor authInterceptor;

    @Autowired
    public WebConfig(LoggingInterceptor loggingInterceptor, AuthInterceptor authInterceptor) {
        this.loggingInterceptor = loggingInterceptor;
        this.authInterceptor = authInterceptor;
    }

    /**
     * 配置全局CORS
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .maxAge(3600);
    }

    /**
     * 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 日志拦截器
        registry.addInterceptor(loggingInterceptor)
                .addPathPatterns("/**");
                
        // 认证拦截器
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/reviews/**", "/api/favorites/**")  // 需要认证的路径
                .excludePathPatterns(
                    "/api/auth/**",                    // 认证相关接口
                    "/api/reviews/spot/**",            // 获取景点评论
                    "/api/reviews/stats/**",           // 获取景点评论统计
                    "/api/favorites/check/**"          // 检查收藏状态
                );
    }
} 