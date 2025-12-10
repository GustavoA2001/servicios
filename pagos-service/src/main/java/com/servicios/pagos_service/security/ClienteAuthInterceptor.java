package com.servicios.pagos_service.security;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import io.jsonwebtoken.Claims;

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
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
        }

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

            // --- MICROSERVICIOS INTERNOS ---
            if ("SYSTEM".equalsIgnoreCase(rol)) {
                System.out.println("Pago-Service: acceso SISTEMA");
                return true;
            }

            // --- CLIENTE ---
            if ("Cliente".equalsIgnoreCase(rol)) {
                Integer clienteId = claims.get("usuarioId", Integer.class);
                request.setAttribute("clienteId", clienteId);
                System.out.println("Pago-Service: acceso Cliente con clienteId=" + clienteId);
                return true;
            }

            // --- ADMIN ---
            if ("Admin".equalsIgnoreCase(rol)) {
                System.out.println("Pago-Service: acceso Admin");
                return true;
            }

            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acceso denegado. Rol no permitido");
            return false;

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
            return false;
        }
    }
}
