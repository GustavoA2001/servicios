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
    private final AuditoriaInterceptor auditoriaInterceptor;

    public WebConfig(AuthValidator authValidator,
                     MensajeInterceptor mensajeInterceptor,
                     AuditoriaInterceptor auditoriaInterceptor) {
        this.authValidator = authValidator;
        this.mensajeInterceptor = mensajeInterceptor;
        this.auditoriaInterceptor = auditoriaInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // ==========================
        // Seguridad por token
        // ==========================
        AdminAuthInterceptor authInterceptor = new AdminAuthInterceptor(authValidator);
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/no-autorizado", "/css/**", "/js/**", "/images/**", "/admin/api/verificar-admin")
                .excludePathPatterns("/api/callback/**");

        // ==========================
        // Interceptor de mensajes
        // ==========================
        registry.addInterceptor(mensajeInterceptor)
                .addPathPatterns("/usuarios/**");

        // ==========================
        // Auditoría
        // ==========================
        registry.addInterceptor(auditoriaInterceptor)
                .addPathPatterns(
                        "/usuarios/**",
                        "/catalogos/**",
                        "/servicios/**",
                        "/login"
                )
                // excluidos
                .excludePathPatterns("/usuarios/mensajes/**");

        System.out.println("=== WebConfig: interceptores registrados ===");
    }
}