package com.servicios.admin_service.security;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

@Component
public class AuthValidator {

    private final JwtUtil jwtUtil;

    public AuthValidator(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // Método que valida el token enviado en el header Authorization
    public Claims validarToken(String header) {

        System.out.println("=======VALIDAR TOKEN =======");
        System.out.println("Header recibido: " + header);

        // Se verifica que el header exista y tenga el formato correcto "Bearer <token>"
        if (header == null || !header.startsWith("Bearer ")) {
            System.out.println("LOG: Token faltante o malformado");
            throw new RuntimeException("Falta el token JWT");
        }

        // Se extrae el token quitando "Bearer "
        String token = header.substring(7);
        System.out.println("Token extraído:");
        System.out.println(token);

        // Se valida el token utilizando JwtUtil
        Claims claims = jwtUtil.validateToken(token);

        // Logs para mostrar datos importantes del JWT ya decodificado
        System.out.println("LOG: Token decodificado correctamente:");
        System.out.println("   Sub: " + claims.getSubject()); // correo o nombre del usuario
        System.out.println("   Rol: " + claims.get("rol", String.class)); // rol del usuario
        System.out.println("   UsuarioId: " + claims.get("usuarioId", Object.class)); // id dentro del token
        System.out.println("   Exp: " + claims.getExpiration()); // fecha de expiración
        
        return claims; // se regresan los claims al interceptor
    }
    
    // Método que valida si el usuario tiene el rol "Administrador"
    public void permitirAdmin(Claims c) {
        System.out.println("======= VERIFICANDO ROL =======");

        // Se extrae el rol del JWT
        String rol = c.get("rol", String.class);
        System.out.println("Rol encontrado en el token: " + rol);

        // Si el rol no coincide con "Administrador", se deniega el acceso
        if (!"Administrador".equalsIgnoreCase(rol)) {
            System.out.println("LOG: Acceso denegado — Rol NO permitido");
            throw new RuntimeException("Acceso denegado. Rol no permitido");
        }

        // Si todo está bien, se permite el acceso
        System.out.println("LOG: Rol 'Administrador' permitido");
    }
    
}
