package com.servicios.notificaciones_service.controller;

import com.servicios.notificaciones_service.model.EmailRequest;
import com.servicios.notificaciones_service.service.CorreoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final CorreoService correoService;

    public NotificacionController(CorreoService correoService) {
        this.correoService = correoService;
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
}