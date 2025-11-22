package com.servicios.admin_service.service;

import org.springframework.stereotype.Service;


@Service
public class MensajeService {
    private String ultimoMensaje = "";

    public void guardarMensaje(String mensaje) {
        this.ultimoMensaje = mensaje;
    }

    public String obtenerYBorrarMensaje() {
        String msg = this.ultimoMensaje;
        this.ultimoMensaje = "";
        return msg;
    }
}