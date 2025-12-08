package com.servicios.servicios_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "imagenes-service", url = "http://localhost:8090")
public interface ImagenesClient {
    @GetMapping("/images/{tipo}/{nombre}")
    String obtenerRutaImagen(@PathVariable("tipo") String tipo,
                             @PathVariable("nombre") String nombre);
}