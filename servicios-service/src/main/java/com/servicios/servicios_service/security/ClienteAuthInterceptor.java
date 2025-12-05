package com.servicios.servicios_service.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ClienteAuthInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    public ClienteAuthInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String token = null;

        // Buscar token en Authorization header
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
        }

        // O buscar token en cookie
        if (token == null && request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if ("jwt".equals(c.getName())) {
                    token = c.getValue();
                    break;
                }
            }
        }

        if (token == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token faltante");
            return false;
        }

        try {
            Claims claims = jwtUtil.validateToken(token);
            String rol = claims.get("rol", String.class);

            if (!"Cliente".equals(rol)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acceso denegado: solo clientes");
                return false;
            }

            // Token válido y rol correcto
            return true;

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
            return false;
        }
    }
}
