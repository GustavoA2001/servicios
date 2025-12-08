package com.servicios.login_service.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "h38FJf93hf92HF93hf93HF93hf93Hf93hf93HF93hf93Hf";

    private final long EXPIRATION = 86400000;

    /**
     * Genera un JWT que contiene:
     * - Subject (email)
     * - rol
     * - usuarioId
     * - fecha de emisión
     * - expiración
     */
    public String generateToken(Long usuarioId, String email, String rol) {
        Date now = new Date();

        return Jwts.builder()
                .setSubject(email)    
                .claim("rol", rol)     
                .claim("usuarioId", usuarioId) 
                .setIssuedAt(now)    
                .setExpiration(new Date(now.getTime() + EXPIRATION))
                .signWith(              // Firma el token con HS256 usando la clave secreta
                        Keys.hmacShaKeyFor(SECRET.getBytes()),
                        SignatureAlgorithm.HS256
                )
                .compact();           
    }

    /**
     * Extrae el rol del usuario desde el JWT.
     */
    public String getRolFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes())) 
                .build()
                .parseClaimsJws(token) // Valida y decodifica el token
                .getBody()
                .get("rol", String.class); 
    }

    /**
     * Obtiene el email (subject) desde el token.
     */
    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // getSubject() = email
    }

    /**
     * Obtiene el usuarioId incluido en el token.
     */
    public Integer getUsuarioIdFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("usuarioId", Integer.class); // claim con el ID del usuario
    }
}
