package com.servicios.admin_service.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servicios.admin_service.client.AuditoriaClient;
import com.servicios.admin_service.model.Auditoria;
import com.servicios.admin_service.model.Usuario;

@Service
public class AuditoriaService {

    @Autowired
    private AuditoriaClient auditoriaClient;

    // Método genérico para cualquier auditoría
    public void enviarAuditoria(Auditoria auditoria) {

        auditoriaClient.enviarAuditoria(auditoria);
    }

    // Método específico para registrar usuario
    public void registrarUsuarioAuditoria(Usuario nuevo, String actorTipo, Long actorId) {
        Auditoria auditoria = new Auditoria();
        auditoria.setUsuarioTipo(actorTipo);
        auditoria.setUsuarioId(actorId);
        auditoria.setServicio("admin-service");
        auditoria.setAccion("Registrar usuario");
        auditoria.setEntidad("Usuario");
        auditoria.setEntidadId(nuevo.getId());
        auditoria.setEndpoint("/usuarios/registrar");
        auditoria.setAntes(null);
        auditoria.setDespues("{\"id\":" + nuevo.getId() + ",\"nombre\":\"" + nuevo.getNombre() + "\"}");

        enviarAuditoria(auditoria); // reutiliza el método genérico
    }

    public List<Auditoria> listarAuditorias() {
        return auditoriaClient.obtenerAuditorias();
    }

    public List<Auditoria> listarAuditoriasFiltradas(String orderBy, String direction,
            LocalDate fecha, String usuarioTipo,
            Long usuarioId, String servicio, String accion) {
        return auditoriaClient.obtenerAuditoriasFiltradas(orderBy, direction, fecha, usuarioTipo, usuarioId, servicio,
                accion);
    }

    public List<Auditoria> listarAuditoriasPorFecha(LocalDate fecha) {
        return auditoriaClient.obtenerAuditoriasPorFecha(fecha);
    }

}
