package com.servicios.auditoria_service.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.servicios.auditoria_service.dao.AuditoriaDAO;
import com.servicios.auditoria_service.model.Auditoria;

@RestController // Indica que este controlador expone endpoints REST (JSON)
@RequestMapping("/auditoria") // URL base
public class AuditoriaController {

    @Autowired
    private AuditoriaDAO auditoriaDao;

    /**
     * Endpoint para registrar una nueva auditoría.
     */
    @PostMapping
    public ResponseEntity<Void> registrar(@RequestBody Auditoria auditoria) {
        System.out.println("========== AUDITORIA RECIBIDA ==========");
        System.out.println("Servicio: " + auditoria.getServicio());
        System.out.println("Endpoint: " + auditoria.getEndpoint());
        System.out.println("Acción: " + auditoria.getAccion());
        System.out.println("Entidad: " + auditoria.getEntidad());
        System.out.println("EntidadId: " + auditoria.getEntidadId());
        System.out.println("UsuarioId: " + auditoria.getUsuarioId());
        System.out.println("UsuarioTipo: " + auditoria.getUsuarioTipo());
        System.out.println("Antes: " + auditoria.getAntes());
        System.out.println("Después: " + auditoria.getDespues());
        System.out.println("Fecha: " + auditoria.getFecha());
        System.out.println("========================================");
    
        auditoriaDao.save(auditoria);
        return ResponseEntity.ok().build();
    }
    

    /**
     * Endpoint para listar todas las auditorías registradas.
     */
    @GetMapping
    public List<Auditoria> listar() {
        return auditoriaDao.findAll();
    }


    @GetMapping("/filtrar")
    public List<Auditoria> listarPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return auditoriaDao.findByFecha(fecha);
    }
}
