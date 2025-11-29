package com.servicios.login_service.conttroller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servicios.login_service.service.AuthService;

@RestController
@RequestMapping("/auth/api")
public class AuthRestController {

    private final AuthService authService;

    public AuthRestController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {

        System.out.println("===================");
        System.out.println(new BCryptPasswordEncoder().matches("alegre", "$2a$10$Qy1uprfTu1irYTusCUXvJuP2kzk5HZGQER2W0y4K6WGW4CNP39qfW"));
        System.out.println("===================");

        try {
            String email = body.get("email");
            String password = body.get("password");
    
            String token = authService.login(email, password);
    
            return ResponseEntity.ok(Map.of(
                    "status", "OK",
                    "message", "Inicio de sesión exitoso",
                    "token", token
            ));

        } catch (RuntimeException e) {
    
            String msg = e.getMessage();
    
            if (msg.equals("Usuario no encontrado")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        Map.of(
                                "status", "ERROR",
                                "message", "Usuario no encontrado"
                        )
                );
            }
    
            if (msg.equals("Credenciales inválidas")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                        Map.of(
                                "status", "ERROR",
                                "message", "Credenciales inválidas"
                        )
                );
            }
    
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of(
                            "status", "ERROR",
                            "message", "Ocurrió un error inesperado"
                    )
            );
        }
    }
    
}