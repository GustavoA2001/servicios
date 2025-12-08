package com.servicios.admin_service.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private final String SECRET = "h38FJf93hf92HF93hf93HF93hf93Hf93hf93HF93hf93Hf"; 

    // Método que valida la firma, integridad y estructura del token JWT
    public Claims validateToken(String token) {
        System.out.println("======= JwtUtil.validateToken() =======");
        System.out.println("Token a validar:");
        System.out.println(token);

        try {
            // Se construye un parser de JWT con la llave secreta usada para firmarlo
            Claims c = Jwts
                    .parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes())) // clave convertida a bytes
                    .build()
                    // Se valida la firma y decodifica el token
                    .parseClaimsJws(token)
                    .getBody(); // Se obtienen los claims (payload)

            System.out.println("LOG: Firma y estructura válidas.");
            return c; // Se devuelven los claims si todo está correcto

        } catch (Exception e) {
            // Cualquier excepción significa token inválido o manipulado
            System.out.println("ERROR EN TOKEN: " + e.getMessage());
            throw new RuntimeException("Token inválido");
        }
    }
}
