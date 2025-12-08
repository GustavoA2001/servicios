package com.servicios.login_service.controller;

import java.util.Map;

import org.springframework.http.ResponseCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servicios.login_service.security.JwtUtil;
import com.servicios.login_service.service.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController // Indica que este controlador expone endpoints REST (JSON)
@RequestMapping("/auth/api") // // Prefijo común, para la ruta debe ir antes para acceder
public class AuthRestController {

    private final AuthService authService; // Servicio que valida credenciales y genera token
    private final JwtUtil jwtUtil; // Utilidad para manipular JWT (extraer rol, datos, etc.)

    public AuthRestController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Recibe email, password y tipo (cliente/empleado/admin),
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body,
            HttpServletResponse response,
            HttpServletRequest request) {
        try {
            String email = body.get("email");
            String password = body.get("password");
            String tipo = body.get("tipo");

            // Guardar en el request para el interceptor
            request.setAttribute("loginEmail", email);
            request.setAttribute("loginTipo", tipo);

            String token = authService.login(email, password, tipo);

            ResponseCookie cookie = ResponseCookie.from("jwt", token)
                    .httpOnly(true)
                    .path("/")
                    .maxAge(3600)
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            String rol = jwtUtil.getRolFromToken(token);

            Map<String, Object> respuesta = Map.of(
                    "status", "OK",
                    "message", "Inicio de sesión exitoso",
                    "token", token,
                    "rol", rol);

            // Guardar la respuesta en el request para que el interceptor la use
            request.setAttribute("jwtToken", token);
            request.setAttribute("auditoriaRespuesta", respuesta);

            return ResponseEntity.ok(respuesta);

        } catch (RuntimeException e) {
            Map<String, Object> respuestaError = Map.of(
                    "status", "ERROR",
                    "message", e.getMessage());

            request.setAttribute("auditoriaRespuesta", respuestaError);

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respuestaError);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response,
                                    HttpServletRequest request) { 
    
        // Extraer token de la cookie antes de borrarla
        String token = null;
        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if ("jwt".equals(c.getName())) {
                    token = c.getValue();
                    break;
                }
            }
        }
    
        if (token != null) {
            request.setAttribute("jwtToken", token);
        }
    
        // Ahora sí borramos la cookie
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    
        Map<String,Object> respuesta = Map.of(
                "status", "OK",
                "message", "Sesión cerrada correctamente"
        );
    
        request.setAttribute("auditoriaRespuesta", respuesta);
    
        return ResponseEntity.ok(respuesta);
    }
    

}
