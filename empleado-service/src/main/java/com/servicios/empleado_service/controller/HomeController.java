package com.servicios.empleado_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String redirectRoot() {
        // Redirige la raíz hacia la página de servicios del empleado
        return "redirect:/empleado/servicios";
    }
}
