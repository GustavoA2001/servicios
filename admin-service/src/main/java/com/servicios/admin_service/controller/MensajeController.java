package com.servicios.admin_service.controller;

import com.servicios.admin_service.service.MensajeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mensajes")
public class MensajeController {

    private final MensajeService mensajeService;

    public MensajeController(MensajeService mensajeService) {
        this.mensajeService = mensajeService;
    }

    @GetMapping("/ultimo")
    public String obtenerUltimoMensaje() {
        return mensajeService.obtenerYBorrarMensaje();
    }
}