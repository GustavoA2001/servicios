package com.servicios.admin_service.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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

    /**Método genérico para registrar cambios con 'antes' y 'después'. 
     * Este es el que deben invocar los Services de negocio. 
     * */
    public void registrarCambio(String servicio, 
                                String accion, 
                                String entidad, 
                                Long entidadId, 
                                Object antes, 
                                Object despues, 
                                String usuarioTipo, 
                                Long usuarioId, 
                                String endpoint) { 
        try { 
            ObjectMapper mapper = new ObjectMapper(); 
            mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule()); 
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            
            String antesJson = (antes != null) ? mapper.writeValueAsString(antes) : null; 
            String despuesJson = (despues != null) ? mapper.writeValueAsString(despues) : null; 
            Auditoria auditoria = new Auditoria(); 
            auditoria.setServicio(servicio); 
            auditoria.setAccion(accion); 
            auditoria.setEntidad(entidad); 
            auditoria.setEntidadId(entidadId); 
            auditoria.setAntes(antesJson); 
            auditoria.setDespues(despuesJson); 
            auditoria.setUsuarioTipo(usuarioTipo != null ? usuarioTipo : "Anonimo"); 
            auditoria.setUsuarioId(usuarioId != null ? usuarioId : 0L); 
            auditoria.setEndpoint(endpoint); 

            System.out.println("=======");
            System.out.println(auditoria);
            System.out.println("=======");
            
            enviarAuditoria(auditoria); 
        } catch (Exception e) { 
            System.out.println("[AuditoriaService] Error serializando/enviando auditoría: " + e.getMessage()); 
        } 
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
