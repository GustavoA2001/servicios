package com.servicios.notificaciones_service.controller;

import com.servicios.notificaciones_service.model.EmailRequest;
import com.servicios.notificaciones_service.model.Notificacion;
import com.servicios.notificaciones_service.service.CorreoService;
import com.servicios.notificaciones_service.service.NotificacionService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final CorreoService correoService;

    private final NotificacionService service;

    public NotificacionController(CorreoService correoService, NotificacionService service) {
        this.correoService = correoService;
        this.service = service;
    }

    @PostMapping("/enviarCorreo")
    public ResponseEntity<String> enviarCorreo(@RequestBody EmailRequest emailRequest) {
        try {
            correoService.enviarCorreo(emailRequest);
            return ResponseEntity.ok("Correo procesado para " + emailRequest.getDestinatario());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Error al procesar correo: " + e.getMessage());
        }
    }

    @PostMapping("/enviar")
    public void enviar(@RequestBody Notificacion notificacion) throws SQLException {
        service.enviar(notificacion);
    }

    @GetMapping("/{usuarioId}")
    public List<Notificacion> historial(@PathVariable Long usuarioId) throws SQLException {
        return service.historial(usuarioId);
    }

    @GetMapping("/globales")
    public List<Notificacion> globales() throws SQLException {
        return service.globales();
    }

}