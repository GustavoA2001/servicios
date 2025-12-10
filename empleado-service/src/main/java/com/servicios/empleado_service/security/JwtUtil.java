package com.servicios.empleado_service.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "h38FJf93hf92HF93hf93HF93hf93Hf93hf93HF93hf93Hf"; 
    private final long EXPIRATION = 3600000; // 1 hora

    // Generar un token para TRABAJADOR
    public String generarTokenTrabajador(Integer usuarioId, String email) {
        return Jwts.builder()
                .setSubject(email)                // el correo del trabajador
                .claim("rol", "TRABAJADOR")       // rol fijo TRABAJADOR
                .claim("usuarioId", usuarioId)    // id del empleado
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    // Validar token y devolver los claims
    public Claims validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new RuntimeException("Token inválido: " + e.getMessage());
        }
    }
}
