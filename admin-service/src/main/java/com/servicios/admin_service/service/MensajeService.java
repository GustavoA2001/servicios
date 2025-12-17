package com.servicios.admin_service.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.stereotype.Service;

import com.servicios.admin_service.controller.MensajeStreamController;

@Service
public class MensajeService {
    private final Queue<String> mensajes = new LinkedList<>();
    private MensajeStreamController streamController;

    public void setStreamController(MensajeStreamController streamController) {
        this.streamController = streamController;
    }

    public void guardarMensaje(String mensaje) {
        mensajes.add(mensaje);
        System.out.println("[MensajeService] Mensaje agregado a la cola: " + mensaje);

        // Notificar inmediatamente a los clientes conectados
        if (streamController != null) {
            streamController.broadcast(mensaje);
        }
    }

    public List<String> obtenerYBorrarTodos() {
        List<String> result = new ArrayList<>(mensajes);
        mensajes.clear();
        return result;
    }
}