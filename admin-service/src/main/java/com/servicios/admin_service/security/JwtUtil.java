package com.servicios.admin_service.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "h38FJf93hf92HF93hf93HF93hf93Hf93hf93HF93hf93Hf"; 

    // Tiempo de expiración: 1 hora
    private final long EXPIRATION = 3600000;

    // Generar un token para Admin
    public String generarTokenAdmin(Integer usuarioId) {
        return Jwts.builder()
                .setSubject("admin")
                .claim("rol", "Admin")
                .claim("usuarioId", usuarioId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    // Generar un token para Cliente
    public String generarTokenCliente(Integer usuarioId) {
        return Jwts.builder()
                .setSubject("cliente")
                .claim("rol", "Cliente")
                .claim("usuarioId", usuarioId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    // Validar token
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
