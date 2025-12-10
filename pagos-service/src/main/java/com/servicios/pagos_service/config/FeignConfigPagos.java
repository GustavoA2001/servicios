package com.servicios.pagos_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.servicios.pagos_service.security.JwtUtil;

import feign.RequestInterceptor;

@Configuration
public class FeignConfigPagos {

    private final JwtUtil jwtUtil;

    public FeignConfigPagos(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            String token = jwtUtil.generarTokenSistema();
            requestTemplate.header("Authorization", "Bearer " + token);
        };
    }
}
