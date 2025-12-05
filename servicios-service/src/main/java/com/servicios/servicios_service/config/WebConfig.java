package com.servicios.servicios_service.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.servicios.servicios_service.security.ClienteAuthInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ClienteAuthInterceptor clienteAuthInterceptor;

    public WebConfig(ClienteAuthInterceptor clienteAuthInterceptor) {
        this.clienteAuthInterceptor = clienteAuthInterceptor;
    }

    @SuppressWarnings("null")
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(clienteAuthInterceptor)
                .addPathPatterns("/**"); // protege todas las rutas
    }
}
