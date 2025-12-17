package com.servicios.admin_service.controller;

import com.servicios.admin_service.service.MensajeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class MensajeStreamController {

    private final MensajeService mensajeService;
    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public MensajeStreamController(MensajeService mensajeService) {
        this.mensajeService = mensajeService;
        mensajeService.setStreamController(this);
    }
    

    @GetMapping("/api/mensajes/stream")
    public SseEmitter streamMensajes() {
        SseEmitter emitter = new SseEmitter();
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        return emitter;
    }

    // Método para enviar mensajes a todos los clientes conectados
    public void enviarMensajes() {
        var mensajes = mensajeService.obtenerYBorrarTodos();
        for (String msg : mensajes) {
            for (SseEmitter emitter : emitters) {
                try {
                    emitter.send(SseEmitter.event().name("mensaje").data(msg));
                } catch (IOException e) {
                    emitters.remove(emitter);
                }
            }
        }
    }

    // Broadcast global
    public void broadcast(String msg) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().name("mensaje").data(msg));
            } catch (IOException e) {
                emitters.remove(emitter);
            }
        }
    }

    
}