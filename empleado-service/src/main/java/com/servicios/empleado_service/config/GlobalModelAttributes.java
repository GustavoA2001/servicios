package com.servicios.empleado_service.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@Component
public class GlobalModelAttributes {

    @ModelAttribute
    public void addGlobalAttributes(Model model, HttpServletRequest request, HttpSession session) {
        Object nombre = session.getAttribute("empleadoNombre");
        Object id = session.getAttribute("empleadoId");
        Object email = session.getAttribute("empleadoEmail");

        model.addAttribute("empleadoNombre", nombre != null ? nombre : null);
        model.addAttribute("empleadoId", id != null ? id : 0);
        model.addAttribute("empleadoEmail", email != null ? email : null);

        String uri = request.getRequestURI();
        model.addAttribute("currentUri", uri != null ? uri : "");
    }
}
