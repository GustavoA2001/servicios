package com.servicios.servicios_service.security;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;

@Component
public class AuthValidator {

    private final JwtUtil jwtUtil;

    public AuthValidator(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public Claims validarToken(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new RuntimeException("Falta el token JWT");
        }

        String token = header.substring(7);
        return jwtUtil.validateToken(token);
    }

    public void permitirCliente(Claims c) {
        String rol = c.get("rol", String.class);
        if (!"Cliente".equalsIgnoreCase(rol)) {
            throw new RuntimeException("Acceso denegado. Solo clientes pueden entrar");
        }
    }
}
