package com.servicios.login_service.controller;

import java.util.Map;

import org.springframework.http.ResponseCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servicios.login_service.security.JwtUtil;
import com.servicios.login_service.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth/api")
public class AuthRestController {

    private final AuthService authService;
    private final JwtUtil jwtUtil; // inyectamos también JwtUtil

    public AuthRestController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body, HttpServletResponse response) {
        try {
            String email = body.get("email");
            String password = body.get("password");
            String tipo = body.get("tipo");

            String token = authService.login(email, password, tipo);

            ResponseCookie cookie = ResponseCookie.from("jwt", token)
                    .httpOnly(true)
                    .path("/")
                    .maxAge(3600)
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            // ahora usamos directamente jwtUtil
            String rol = jwtUtil.getRolFromToken(token);

            return ResponseEntity.ok(Map.of(
                    "status", "OK",
                    "message", "Inicio de sesión exitoso",
                    "token", token,
                    "rol", rol
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    Map.of("status", "ERROR", "message", e.getMessage()));
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(Map.of(
                "status", "OK",
                "message", "Sesión cerrada correctamente"));
    }
}