package com.servicios.servicios_service.controller;


import com.servicios.servicios_service.model.Servicio;
import com.servicios.servicios_service.service.ServicioService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ServicioController extends BaseController {

    @Autowired
    private ServicioService servicioService;

    @GetMapping({"/","/servicios"})
    public String listarServicios(Model model) {
        model.addAttribute("servicios", servicioService.listarServicios());
        return "servicios";
    }

    @GetMapping("/servicios/{id}")
    public String detalleServicio(@PathVariable("id") Integer id, Model model) {
        Servicio servicio = servicioService.obtenerServicioCompleto(id);
        model.addAttribute("servicio", servicio);
        model.addAttribute("calificaciones", servicio.getCalificaciones());
        return "detalle_servicios";
    }
    
    
}