package com.servicios.admin_service.config;

import com.servicios.admin_service.security.AdminAuthInterceptor;
import com.servicios.admin_service.security.AuthValidator;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthValidator authValidator;
    private final MensajeInterceptor mensajeInterceptor;

    public WebConfig(AuthValidator authValidator, MensajeInterceptor mensajeInterceptor) {
        this.authValidator = authValidator;
        this.mensajeInterceptor = mensajeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Seguridad por token
        AdminAuthInterceptor authInterceptor = new AdminAuthInterceptor(authValidator);
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/no-autorizado", 
                "/css/**", 
                "/js/**", 
                "/images/**", 
                "/admin/api/verificar-admin")
                .excludePathPatterns("/api/callback/**"); // <-- excluye los callbacks

        // Mensajes (solo vistas de usuarios)
        registry.addInterceptor(mensajeInterceptor)
                .addPathPatterns("/usuarios/**");

        System.out.println("=== WebConfig: interceptores registrados ===");
    }
}
