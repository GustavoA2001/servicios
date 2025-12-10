package com.servicios.admin_service.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.servicios.admin_service.security.JwtUtil;

@Configuration
public class FeignConfig {

    private final JwtUtil jwtUtil;

    public FeignConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            // Genera un token válido de Admin
            String token = jwtUtil.generarTokenAdmin(1);
            requestTemplate.header("Authorization", "Bearer " + token);
        };
    }
}
