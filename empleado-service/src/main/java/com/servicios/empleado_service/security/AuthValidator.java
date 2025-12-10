package com.servicios.empleado_service.security;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;


@Component
public class AuthValidator {

    private final JwtUtil jwtUtil;

    public AuthValidator(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // Valida el token enviado en el header Authorization o cookie
    public Claims validarToken(String header) {
        System.out.println("======= VALIDAR TOKEN (Empleado) =======");
        System.out.println("Header recibido: " + header);

        if (header == null || !header.startsWith("Bearer ")) {
            throw new RuntimeException("Falta el token JWT");
        }

        String token = header.substring(7);
        Claims claims = jwtUtil.validateToken(token);

        System.out.println("LOG: Token decodificado correctamente:");
        System.out.println("   Sub: " + claims.getSubject());
        System.out.println("   Rol: " + claims.get("rol", String.class));
        System.out.println("   UsuarioId: " + claims.get("usuarioId", Object.class));
        System.out.println("   Exp: " + claims.getExpiration());

        return claims;
    }

    // Verifica que el rol sea TRABAJADOR (o EMPLEADO si lo quieres aceptar también)
    public void permitirTrabajador(Claims c) {
        System.out.println("======= VERIFICANDO ROL TRABAJADOR =======");

        String rol = c.get("rol", String.class);
        System.out.println("Rol encontrado en el token: " + rol);

        if (!"TRABAJADOR".equalsIgnoreCase(rol) && !"EMPLEADO".equalsIgnoreCase(rol)) {
            throw new RuntimeException("Acceso denegado. Rol no permitido");
        }

        System.out.println("LOG: Rol TRABAJADOR permitido");
    }
}
