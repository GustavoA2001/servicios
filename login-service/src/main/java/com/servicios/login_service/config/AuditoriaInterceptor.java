package com.servicios.login_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.servicios.login_service.security.JwtUtil;
import com.servicios.login_service.service.AuditoriaService;
import com.servicios.login_service.model.Auditoria;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;

@Component
public class AuditoriaInterceptor implements HandlerInterceptor {

    @Autowired
    private AuditoriaService auditoriaService;

    @Autowired
    private JwtUtil jwtUtil;

    private final ObjectMapper mapper = new ObjectMapper();

    @SuppressWarnings("null")
    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex) {
        String method = request.getMethod();
        String uri = request.getRequestURI();

        System.out.println("=== AuditoriaInterceptor activado ===");
        System.out.println("Método: " + method + " | URI: " + uri);

        Auditoria auditoria = new Auditoria();
        auditoria.setServicio("login-service");
        auditoria.setEndpoint(uri);

        // Dentro de afterCompletion, justo después de leer la cookie:
        String token = (String) request.getAttribute("jwtToken");
        if (token == null) {
            if (request.getCookies() != null) {
                for (Cookie c : request.getCookies()) {
                    if ("jwt".equals(c.getName())) {
                        token = c.getValue();
                        break;
                    }
                }
            }
        }
        System.out.println("Token leído en interceptor: " + token);
        

        System.out.println("Token leído en interceptor: " + token);

        if (token != null && !token.isEmpty()) {
            try {
                Long usuarioIdFromToken = jwtUtil.getUsuarioIdFromToken(token).longValue();
                String rolFromToken = jwtUtil.getRolFromToken(token);
                String emailFromToken = jwtUtil.getEmailFromToken(token);

                // Mostrar en consola lo que se decodificó
                System.out.println("UsuarioId del token: " + usuarioIdFromToken);
                System.out.println("Rol del token: " + rolFromToken);
                System.out.println("Email del token: " + emailFromToken);

                auditoria.setUsuarioId(usuarioIdFromToken);
                auditoria.setUsuarioTipo(rolFromToken);

            } catch (Exception e) {
                System.out.println("Error al decodificar token en interceptor: " + e.getMessage());
                auditoria.setUsuarioId(0L);
                auditoria.setUsuarioTipo("Anonimo");
            }
        } else {
            System.out.println("No se encontró token en la cookie");
            auditoria.setUsuarioId(0L);
            auditoria.setUsuarioTipo("Anonimo");
        }

        // === Caso LOGIN ===
        if (uri.endsWith("/login")) {
            System.out.println("Interceptando LOGIN...");

            auditoria.setEntidad("Login");
            auditoria.setEntidadId(auditoria.getUsuarioId());

            String email = (String) request.getAttribute("loginEmail");
            String tipo = (String) request.getAttribute("loginTipo");
            auditoria.setAntes("{\"email\":\"" + email + "\",\"tipo\":\"" + tipo + "\"}");
            
            Object respuesta = request.getAttribute("auditoriaRespuesta");
            if (respuesta != null) {
                try {
                    auditoria.setDespues(mapper.writeValueAsString(respuesta));
                } catch (Exception e) {
                    auditoria.setDespues("{\"status\":\"ERROR\",\"message\":\"No se pudo serializar respuesta\"}");
                }
            }

            auditoria.setAccion(response.getStatus() == 200 ? "Ingreso exitoso" : "Ingreso fallido");
        }

        // === Caso LOGOUT ===
        else if (uri.endsWith("/logout")) {
            System.out.println("Interceptando LOGOUT...");
        
            auditoria.setEntidad("Login");
            auditoria.setEntidadId(auditoria.getUsuarioId());
        
            if (token != null && !token.isEmpty()) {
                try {
                    String emailFromToken = jwtUtil.getEmailFromToken(token);
                    System.out.println("Email del token en logout: " + emailFromToken);
                    auditoria.setAntes("{\"estado\":\"logueado\",\"email\":\"" + emailFromToken + "\"}");
                } catch (Exception e) {
                    auditoria.setAntes("{\"estado\":\"logueado\"}");
                }
            } else {
                System.out.println("No se encontró token en logout");
                auditoria.setAntes("{\"estado\":\"logueado\"}");
            }
        
            auditoria.setAccion("Salida del sistema");
        
            Object respuesta = request.getAttribute("auditoriaRespuesta");
            if (respuesta != null) {
                try {
                    auditoria.setDespues(mapper.writeValueAsString(respuesta));
                } catch (Exception e) {
                    auditoria.setDespues("{\"status\":\"OK\",\"message\":\"Sesión cerrada\"}");
                }
            }
        }
        

        // === Caso CRUD ===
        else if (method.equals("POST") || method.equals("PUT") || method.equals("DELETE")) {
            System.out.println("Interceptando CRUD...");

            auditoria.setAccion(method + " " + uri);
            auditoria.setEntidad(detectarEntidad(uri));
            auditoria.setEntidadId(extraerId(uri));
            auditoria.setAntes(null);
            auditoria.setDespues(null);
        }

        System.out.println("Auditoría construida: " + auditoria);
        auditoriaService.enviarAuditoria(auditoria);
        System.out.println("Auditoría enviada al servicio auditoria");
    }

    private String detectarEntidad(String uri) {
        if (uri.startsWith("/usuarios"))
            return "Usuario";
        if (uri.startsWith("/catalogos/distritos"))
            return "Distrito";
        if (uri.startsWith("/catalogos/especialidades"))
            return "Especialidad";
        if (uri.startsWith("/catalogos/roles"))
            return "Rol";
        if (uri.startsWith("/servicios"))
            return "Servicio";
        return "Desconocido";
    }

    private Long extraerId(String uri) {
        try {
            String[] parts = uri.split("/");
            return Long.parseLong(parts[parts.length - 1]);
        } catch (Exception e) {
            return null;
        }
    }
}
