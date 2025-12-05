package com.servicios.admin_service.config;

import com.servicios.admin_service.security.AdminAuthInterceptor;
import com.servicios.admin_service.security.AuthValidator;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthValidator authValidator;

    public WebConfig(AuthValidator authValidator) {
        this.authValidator = authValidator;
    }

    @SuppressWarnings("null")
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        AdminAuthInterceptor interceptor = new AdminAuthInterceptor(authValidator);
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**") // Proteger todo
                .excludePathPatterns("/no-autorizado")   // Excepto estas rutas
                .excludePathPatterns("/css/**")
                .excludePathPatterns("/js/**")
                .excludePathPatterns("/images/**")
                .excludePathPatterns("/admin/api/verificar-admin"); // Excluida para prueba
    
        System.out.println("=== WebConfig: interceptor registrado ===");
        System.out.println("Rutas excluidas: /no-autorizado, /css/**, /js/**, /images/**, /admin/api/verificar-admin");
    }
    
}