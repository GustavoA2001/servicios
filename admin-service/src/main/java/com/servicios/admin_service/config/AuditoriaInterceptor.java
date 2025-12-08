package com.servicios.admin_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.servicios.admin_service.model.Auditoria;
import com.servicios.admin_service.service.AuditoriaService;

@Component
public class AuditoriaInterceptor implements HandlerInterceptor {

    @Autowired
    private AuditoriaService auditoriaService;

    @SuppressWarnings("null")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return true;
    }

    @SuppressWarnings("null")
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String method = request.getMethod();
        String uri = request.getRequestURI();
    
        System.out.println("=== Interceptor Auditoría ACTIVADO ===");
        System.out.println("Método HTTP=" + method + ", URI=" + uri);
    
        if (method.equals("POST") || method.equals("PUT") || method.equals("DELETE") || uri.equals("/login")) {
            System.out.println("Condición de auditoría cumplida, construyendo objeto Auditoria...");
    
            Auditoria auditoria = new Auditoria();
            auditoria.setServicio("admin-service");
            auditoria.setAccion(method + " " + uri);
            auditoria.setEndpoint(uri);
    
            // Usar atributos propagados desde AdminAuthInterceptor
            String rol = (String) request.getAttribute("usuarioRol");
            Long usuarioId = (Long) request.getAttribute("usuarioId");
    
            auditoria.setUsuarioTipo(rol != null ? rol : "Anonimo");
            auditoria.setUsuarioId(usuarioId != null ? usuarioId : 0L);
    
            System.out.println("=== DATOS PROPAGADOS DESDE AdminAuthInterceptor ===");
            System.out.println("ROL=" + rol + ", usuarioId=" + usuarioId);
    
            // === Entidad dinámica según endpoint ===
            if (uri.startsWith("/usuarios")) {
                auditoria.setEntidad("Usuario");
            } else if (uri.startsWith("/catalogos/distritos")) {
                auditoria.setEntidad("Distrito");
            } else if (uri.startsWith("/catalogos/especialidades")) {
                auditoria.setEntidad("Especialidad");
            } else if (uri.startsWith("/catalogos/roles")) {
                auditoria.setEntidad("Rol");
            } else if (uri.startsWith("/servicios")) {
                auditoria.setEntidad("Servicio");
            } else if (uri.equals("/login")) {
                auditoria.setEntidad("Login");
            } else {
                auditoria.setEntidad("Desconocido");
            }
            System.out.println("Entidad detectada=" + auditoria.getEntidad());
    
            // EntidadID desde la URL
            String[] parts = uri.split("/");
            try {
                Long entidadId = Long.parseLong(parts[parts.length - 1]);
                auditoria.setEntidadId(entidadId);
                System.out.println("EntidadId detectada=" + entidadId);
            } catch (NumberFormatException e) {
                System.out.println("No se pudo parsear entidadId desde la URL.");
                auditoria.setEntidadId(null);
            }
    
            auditoria.setAntes(null);
            auditoria.setDespues(null);
    
            System.out.println("Enviando auditoría al AuditoriaService...");
            auditoriaService.enviarAuditoria(auditoria);
    
            System.out.println("AUDITORIA ENVIADA OBJETO: " + auditoria);
        } else {
            System.out.println("Condición de auditoría NO cumplida, no se audita esta petición.");
        }
    }
    
}
