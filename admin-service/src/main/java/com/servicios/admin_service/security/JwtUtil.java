package com.servicios.admin_service.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;


@Component
public class JwtUtil {
    private final String SECRET = "h38FJf93hf92HF93hf93HF93hf93Hf93hf93HF93hf93Hf"; // CLAVE REPETIDA EN TODOS LOS JWT
     
    public Claims validateToken(String token) {
        System.out.println("======= 🟦 JwtUtil.validateToken() =======");
        System.out.println("🟦 Token a validar:");
        System.out.println(token);
    
        try {
            Claims c = Jwts
                    .parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
    
            System.out.println("✔ LOG: Firma y estructura válidas.");
            return c;
    
        } catch (Exception e) {
            System.out.println("❌ ERROR EN TOKEN: " + e.getMessage());
            throw new RuntimeException("Token inválido");
        }
    }
}