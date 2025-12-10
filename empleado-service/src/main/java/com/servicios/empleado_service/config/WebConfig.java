package com.servicios.empleado_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.servicios.empleado_service.security.AuthValidator;
import com.servicios.empleado_service.security.EmpleadoAuthInterceptor;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthValidator authValidator;

    public WebConfig(AuthValidator authValidator) {
        this.authValidator = authValidator;
    }

    @SuppressWarnings("null")
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        EmpleadoAuthInterceptor authInterceptor = new EmpleadoAuthInterceptor(authValidator);
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/no-autorizado", "/css/**", "/js/**", "/images/**");

        System.out.println("=== WebConfig: interceptores registrados en empleado-service ===");
    }
}