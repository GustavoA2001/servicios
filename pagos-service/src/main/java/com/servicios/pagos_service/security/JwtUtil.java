package com.servicios.pagos_service.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

import org.springframework.stereotype.Component;


@Component
public class JwtUtil {

    private final String SECRET = "h38FJf93hf92HF93hf93HF93hf93Hf93hf93HF93hf93Hf"; // CLAVE COMPARTIDA

    // ========= GENERAR TOKEN INTERNO PARA FEIGN =========
    public String generarTokenSistema() {
        return Jwts.builder()
                .setSubject("pagos-service")
                .claim("rol", "SYSTEM")     // puedes validar este rol en pedido-service
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hora
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .compact();
    }

    // ========= VALIDAR TOKEN =========
    public Claims validateToken(String token) {
        System.out.println("======= JwtUtil.validateToken() =======");
        System.out.println("Token a validar:");
        System.out.println(token);

        try {
            Claims c = Jwts
                    .parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            System.out.println("LOG: Firma y estructura válidas.");
            return c;

        } catch (Exception e) {
            System.out.println("ERROR EN TOKEN: " + e.getMessage());
            throw new RuntimeException("Token inválido");
        }
    }
}