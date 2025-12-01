package com.servicios.admin_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class AdminHomeController {

    @GetMapping("/")
    public String inicio() {
        return "index";
    }
    

    @GetMapping("/no-autorizado")
    public String noAutorizado() {
        return "no_autorizado";  // crea esta página simple
    }

    @GetMapping("/admin/api/verificar-admin")
    @ResponseBody
    public String verificarAdmin() {
        System.out.println("=== /admin/api/verificar-admin llamado ===");
        return "Acceso permitido";
    }

}