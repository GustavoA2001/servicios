package com.servicios.admin_service.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.stereotype.Service;

@Service
public class MensajeService {
    private final Queue<String> mensajes = new LinkedList<>();

    public void guardarMensaje(String mensaje) {
        mensajes.add(mensaje);
        System.out.println("[MensajeService] Mensaje agregado a la cola: " + mensaje);
    }

    public String obtenerYBorrarMensaje() {
        String msg = mensajes.poll();
        System.out.println("[MensajeService] Mensaje obtenido y borrado: " + msg);
        return msg;
    }

    public List<String> obtenerYBorrarTodos() {
        List<String> result = new ArrayList<>(mensajes);
        mensajes.clear();
        System.out.println("[MensajeService] Mensajes consumidos: " + result);
        return result;
    }    

    public List<String> obtenerTodos() {
        System.out.println("[MensajeService] Mensajes actuales en cola: " + mensajes);
        return new ArrayList<>(mensajes);
    }
}
