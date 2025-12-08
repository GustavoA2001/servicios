package com.servicios.login_service.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import org.springframework.http.ResponseCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    @GetMapping("/logout")
    public void logout(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
    
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
            System.out.println("Token capturado en AuthController.logout: " + token);
            request.setAttribute("jwtToken", token);
        }
    
        // Ahora sí borramos la cookie
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0) // expira inmediatamente
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    
        // Guardar respuesta para el interceptor
        Map<String,Object> respuesta = Map.of(
                "status", "OK",
                "message", "Sesión cerrada correctamente"
        );
        request.setAttribute("auditoriaRespuesta", respuesta);
    
        // Redirigir al login (vista)
        response.sendRedirect("/auth/login");
    }
    
}
