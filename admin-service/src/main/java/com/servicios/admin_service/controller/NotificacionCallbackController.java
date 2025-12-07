package com.servicios.admin_service.controller;

import com.servicios.admin_service.service.MensajeService;
import com.servicios.admin_service.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/callback")
public class NotificacionCallbackController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MensajeService mensajeService;

    @PostMapping("/confirmarCorreo")
    public void recibirConfirmacion(@RequestBody Map<String, String> data) {
        String destinatario = data.get("destinatario");
        String estado = data.get("estado");
    
        System.out.println("[CallbackController] Confirmación recibida: destinatario=" + destinatario + ", estado=" + estado);
    
        usuarioService.confirmarEnvioCorreo(destinatario, estado);
    
        String mensaje = "Correo enviado a " + destinatario + " - Estado: " + estado;
        mensajeService.guardarMensaje(mensaje);
    
        System.out.println("[CallbackController] Mensaje guardado en MensajeService: " + mensaje);
    }
}