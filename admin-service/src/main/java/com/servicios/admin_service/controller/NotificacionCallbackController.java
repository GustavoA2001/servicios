package com.servicios.admin_service.controller;

import com.servicios.admin_service.service.UsuarioService;
import com.servicios.admin_service.service.MensajeService;

import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/callback")
public class NotificacionCallbackController {

    private final UsuarioService usuarioService;
    private final MensajeService mensajeService;

    public NotificacionCallbackController(UsuarioService usuarioService, MensajeService mensajeService) {
        this.usuarioService = usuarioService;
        this.mensajeService = mensajeService;
    }

    @PostMapping("/confirmarCorreo")
    public void recibirConfirmacion(@RequestBody Map<String, String> data) {
        String destinatario = data.get("destinatario");
        String estado = data.get("estado");

        usuarioService.confirmarEnvioCorreo(destinatario, estado);

        // Guardamos el mensaje temporal
        String mensaje = "Correo enviado a " + destinatario + " - Estado: " + estado;
        mensajeService.guardarMensaje(mensaje);

        System.out.println("Mensaje temporal guardado: " + mensaje);
    }
}