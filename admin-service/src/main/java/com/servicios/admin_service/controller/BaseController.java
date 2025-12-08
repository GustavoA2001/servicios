package com.servicios.admin_service.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;

public abstract class BaseController {

    /**
     * Método protegido que agrega al modelo (Model)
     * los datos del usuario autenticado para el menu
     * colocados en el request por el interceptor (AdminAuthInterceptor).
     */
    protected void agregarDatosUsuario(Model model, HttpServletRequest request) {
        Object email = request.getAttribute("usuarioEmail");
        Object rol = request.getAttribute("usuarioRol");

        if (email != null) {
            model.addAttribute("usuarioEmail", email);
        }
        if (rol != null) {
            model.addAttribute("usuarioRol", rol);
        }
    }
}
