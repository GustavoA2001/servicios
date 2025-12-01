package com.servicios.admin_service.security;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

@Component
public class AuthValidator {

    private final JwtUtil jwtUtil;

    public AuthValidator(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public Claims validarToken(String header) {

        System.out.println("======= 🟦 VALIDAR TOKEN =======");
        System.out.println("🟦 Header recibido: " + header);
    
        if (header == null || !header.startsWith("Bearer ")) {
            System.out.println("❌ LOG: Token faltante o malformado");
            throw new RuntimeException("Falta el token JWT");
        }
    
        String token = header.substring(7);
        System.out.println("🟦 Token extraído:");
        System.out.println(token);
    
        Claims claims = jwtUtil.validateToken(token);
    
        System.out.println("✔ LOG: Token decodificado correctamente:");
        System.out.println("   Sub: " + claims.getSubject());
        System.out.println("   Rol: " + claims.get("rol", String.class));
        System.out.println("   Exp: " + claims.getExpiration());
    
        return claims;
    }
    
    
    public void permitirAdmin(Claims c) {
        System.out.println("======= 🟦 VERIFICANDO ROL =======");
    
        String rol = c.get("rol", String.class);
        System.out.println("🟦 Rol encontrado en el token: " + rol);
    
        if (!"Administrador".equalsIgnoreCase(rol)) {
            System.out.println("❌ LOG: Acceso denegado — Rol NO permitido");
            throw new RuntimeException("Acceso denegado. Rol no permitido");
        }
    
        System.out.println("✔ LOG: Rol 'Administrador' permitido");
    }
    
}