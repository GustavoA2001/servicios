package com.servicios.admin_service.client;

import com.servicios.admin_service.model.EmailRequest;
import com.servicios.admin_service.model.Notificacion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
    name = "notificaciones-service",
    url = "http://localhost:8086/api/notificaciones", // Ajusta el puerto según despliegue
    configuration = com.servicios.admin_service.config.FeignConfig.class
)
public interface NotificacionesClient {

    @PostMapping("/enviarCorreo")
    String enviarCorreo(@RequestBody EmailRequest emailRequest);

    @PostMapping("/enviar")
    void enviar(@RequestBody Notificacion notificacion);

    @GetMapping("/{usuarioId}")
    List<Notificacion> historial(@PathVariable("usuarioId") Long usuarioId);

    @GetMapping("/globales")
    List<Notificacion> globales();
}