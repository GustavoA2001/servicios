package com.servicios.login_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servicios.login_service.client.AuditoriaClient;
import com.servicios.login_service.model.Auditoria;

@Service
public class AuditoriaService {

    @Autowired
    private AuditoriaClient auditoriaClient;

    /**
     * Método genérico para enviar cualquier auditoría.
     */
    public void enviarAuditoria(Auditoria auditoria) {
        try {
            auditoriaClient.enviarAuditoria(auditoria);
            System.out.println("Auditoría enviada: " + auditoria);
        } catch (Exception e) {
            System.err.println("Error enviando auditoría: " + e.getMessage());
        }
    }

    /**
     * Atajo para registrar login exitoso o fallido.
     */
    public void registrarLoginAuditoria(Long usuarioId, String usuarioTipo, boolean exito) {
        Auditoria auditoria = new Auditoria();
        auditoria.setUsuarioTipo(usuarioTipo);
        auditoria.setUsuarioId(usuarioId);
        auditoria.setServicio("login-service");
        auditoria.setAccion(exito ? "Ingreso exitoso" : "Ingreso fallido");
        auditoria.setEntidad("Usuario");
        auditoria.setEntidadId(usuarioId);
        auditoria.setEndpoint("/login");
        auditoria.setAntes("{\"email\":\"" + usuarioId + "\",\"tipo\":\"" + usuarioTipo + "\"}");
        auditoria.setDespues("{\"estado\":\"" + (exito ? "logueado" : "fallido") + "\"}");

        enviarAuditoria(auditoria);
    }

    /**
     * Atajo para registrar logout.
     */
    public void registrarLogoutAuditoria(Long usuarioId, String usuarioTipo) {
        Auditoria auditoria = new Auditoria();
        auditoria.setUsuarioTipo(usuarioTipo);
        auditoria.setUsuarioId(usuarioId);
        auditoria.setServicio("login-service");
        auditoria.setAccion("Salida de sesión");
        auditoria.setEntidad("Usuario");
        auditoria.setEntidadId(usuarioId);
        auditoria.setEndpoint("/logout");
        auditoria.setAntes("{\"estado\":\"logueado\"}");
        auditoria.setDespues("{\"estado\":\"desconectado\"}");

        enviarAuditoria(auditoria);
    }
}
