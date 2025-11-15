package com.servicios.servicios_service.controller;

import com.servicios.servicios_service.model.Servicio;
import com.servicios.servicios_service.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ServicioController {

    @Autowired
    private ServicioRepository servicioRepository;

    @GetMapping({"/","/servicios"})
    public String listarServicios(Model model) {
        model.addAttribute("servicios", servicioRepository.findAll());
        return "servicios";
    }

    @GetMapping("/servicios/{id}")
    public String detalleServicio(@PathVariable("id") Integer id, Model model) {
        Servicio servicio = servicioRepository.findById(id).orElse(null);
        if (servicio == null) {
            return "redirect:/servicios";
        }
        model.addAttribute("servicio", servicio);
        return "detalle_servicios";
    }
}

