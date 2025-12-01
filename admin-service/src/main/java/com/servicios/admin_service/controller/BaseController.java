package com.servicios.admin_service.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;

public abstract class BaseController {

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