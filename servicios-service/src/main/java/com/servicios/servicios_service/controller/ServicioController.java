package com.servicios.servicios_service.controller;

import com.servicios.servicios_service.model.CalificacionServicio;
import com.servicios.servicios_service.model.Empleado;
import com.servicios.servicios_service.model.Servicio;
import com.servicios.servicios_service.service.ServicioService;

import java.util.List;

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
        // primero el servicio base
        Servicio servicio = servicioService.obtenerServicioConCalificaciones(id);
    
        // luego le agregamos los trabajadores
        List<Empleado> trabajadores = servicioService.obtenerTrabajadoresPorServicio(id);
        servicio.setEmpleados(trabajadores);
    
        // ya tienes calificaciones dentro del servicio, pero si quieres pasarlas aparte:
        model.addAttribute("servicio", servicio);
        model.addAttribute("calificaciones", servicio.getCalificaciones());
    
        return "detalle_servicios";
    }
    
    
}