package com.servicios.admin_service.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AdminAuthInterceptor implements HandlerInterceptor {

    private final AuthValidator authValidator;

    public AdminAuthInterceptor(AuthValidator authValidator) {
        this.authValidator = authValidator;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {
    
        System.out.println("\n\n======= 🚨 INTERCEPTOR ADMIN-SERVICE 🚨 =======");
        System.out.println("🟦 LOG: URL solicitada → " + request.getRequestURI());
    
        // 1. RUTAS PÚBLICAS -- ELIMINAR A FUTURO
        /* 
        String uri = request.getRequestURI();
        if (uri.equals("/") || uri.equals("/favicon.ico") || uri.startsWith("/css/")
                || uri.startsWith("/js/") || uri.startsWith("/images/")) {
            System.out.println("🟦 LOG: Ruta pública → acceso permitido sin token");
            return true;
        }
        */
    
        // 2. TOKEN POR HEADER
        String header = request.getHeader("Authorization");
        String token = null;
        System.out.println("🟦 LOG: Authorization Header recibido → " + header);
    
        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
            System.out.println("🟦 LOG: Token detectado en HEADER:");
            System.out.println(token);
        } else if (request.getCookies() != null) {
    
            // 3. TOKEN POR COOKIE
            System.out.println("🟦 LOG: Buscando token en COOKIES...");
            for (Cookie c : request.getCookies()) {
                System.out.println("   🟨 Cookie encontrada → " + c.getName() + " = " + c.getValue());
                if ("jwt".equals(c.getName())) {
                    token = c.getValue();
                    System.out.println("🟦 LOG: Token encontrado en cookie JWT:");
                    System.out.println(token);
                    break;
                }
            }
        }
    
        if (token == null) {
            System.out.println("❌ LOG: No se encontró token en header ni cookie.");
            response.sendRedirect("/no-autorizado");
            return false;
        }
    
        try {
            System.out.println("🟦 LOG: Validando token...");
            Claims claims = authValidator.validarToken("Bearer " + token);            

            System.out.println("🟦 LOG: Verificando rol administrador...");            
            authValidator.permitirAdmin(claims);
            System.out.println("✔ LOG: Token válido y rol permitido — acceso concedido");
    
            // Guardar datos del usuario en el request
            request.setAttribute("usuarioEmail", claims.getSubject());
            request.setAttribute("usuarioRol", claims.get("rol", String.class));

            System.out.println();
            return true;

    
        } catch (Exception e) {
            System.out.println("❌ ERROR: " + e.getMessage());
            response.sendRedirect("/no-autorizado");
            return false;
        }
    }

}