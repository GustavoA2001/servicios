package com.servicios.servicios_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController extends BaseController {

    @GetMapping("/contacto")
    public String contacto() {
        return "contacto";
    }

    @GetMapping("/nosotros")
    public String nosotros() {
        return "nosotros";
    }
}