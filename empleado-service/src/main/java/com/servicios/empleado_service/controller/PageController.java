package com.servicios.empleado_service.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controlador ligero para páginas estáticas del panel empleado:
 * - /empleado  -> redirige a /empleado/servicios (controlado por EmpleadoServicioController)
 * - /empleado/pedidos
 * - /empleado/soporte
 *
 * En los métodos de pedidos/soporte puedes añadir model attributes si lo necesitas,
 * p.ej. model.addAttribute("pedidos", pedidoService.findByEmpleadoId(...));
 */
@Controller
@RequestMapping("/empleado")
public class PageController {

    @GetMapping
    public String index() {
        return "redirect:/empleado/servicios";
    }

    @GetMapping("/pedidos")
    public String pedidosPage(Model model, HttpSession session) {
        
        return "empleado/pedidos";

    }

    @GetMapping("/soporte")
    public String soportePage(Model model) {
        return "empleado/soporte";
    }
}
