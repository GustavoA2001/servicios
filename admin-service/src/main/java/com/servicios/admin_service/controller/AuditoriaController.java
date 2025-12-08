package com.servicios.admin_service.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.servicios.admin_service.model.Auditoria;
import com.servicios.admin_service.service.AuditoriaService;

@Controller
@RequestMapping("/auditorias")
public class AuditoriaController {

    @Autowired
    private AuditoriaService auditoriaService;

    @GetMapping
    public String verAuditorias(@RequestParam(required = false) 
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                                Model model) {
    
        List<Auditoria> auditorias;
    
        if (fecha == null) {
            auditorias = auditoriaService.listarAuditorias();
        } else {
            auditorias = auditoriaService.listarAuditoriasPorFecha(fecha);
            model.addAttribute("fechaSeleccionada", fecha); // <-- aquí guardamos la fecha
        }
    
        model.addAttribute("activeMenu", "auditorias");
        model.addAttribute("auditorias", auditorias);
        return "auditorias";
    }
    

}
