package com.servicios.login_service.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuditoriaInterceptor auditoriaInterceptor;

    public WebConfig(AuditoriaInterceptor auditoriaInterceptor) {
        this.auditoriaInterceptor = auditoriaInterceptor;
    }

    @SuppressWarnings("null")
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(auditoriaInterceptor)
            .addPathPatterns("/auth/api/login", "/auth/api/logout", "/auth/logout");
            
        System.out.println("=== WebConfig: AuditoriaInterceptor registrado en login-service ===");
    }
}