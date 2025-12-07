package com.servicios.admin_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servicios.admin_service.service.MensajeService;

@RestController
@RequestMapping("/api/mensajes")
public class MensajeRestController {

    private final MensajeService mensajeService;

    public MensajeRestController(MensajeService mensajeService) {
        this.mensajeService = mensajeService;
    }

    @GetMapping("/todos")
    public List<String> obtenerMensajes() {
        return mensajeService.obtenerYBorrarTodos();
    }
}
