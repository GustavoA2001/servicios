package com.servicios.admin_service.controller;

import com.servicios.admin_service.dao.*;
import com.servicios.admin_service.model.*;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/catalogos")
public class CatalogosController {

    private final CatalogosDAO catalogosDAO;

    public CatalogosController(CatalogosDAO catalogosDAO) {
        this.catalogosDAO = catalogosDAO;
    }

    // Redirección al entrar a /catalogos
    @GetMapping
    public String catalogosRoot(Model model) {
        model.addAttribute("activeMenu", "catalogos");
        return "redirect:/catalogos/distritos";
    }

    @GetMapping("/distritos")
    public String mostrarDistritos(Model model) {
        List<Distrito> distritos = catalogosDAO.obtenerDistritos();
        model.addAttribute("activeMenu", "catalogos");
        model.addAttribute("activeCatalogo", "distritos");
        model.addAttribute("distritos", distritos);
        return "catalogos";
    }

    @GetMapping("/especialidades")
    public String mostrarEspecialidades(Model model) {
        List<Especialidad> especialidades = catalogosDAO.obtenerEspecialidades();
        model.addAttribute("activeMenu", "catalogos");
        model.addAttribute("activeCatalogo", "especialidades");
        model.addAttribute("especialidades", especialidades);
        return "catalogos";
    }

    @GetMapping("/calificaciones")
    public String mostrarCalificaciones(Model model) {
        //List<Calificacion> calificaciones = catalogosDAO.obtenerCalificaciones();
        model.addAttribute("activeMenu", "catalogos");
        model.addAttribute("activeCatalogo", "calificaciones");
        //model.addAttribute("calificaciones", calificaciones);
        return "catalogos";
    }

    @GetMapping("/roles")
    public String mostrarRoles(Model model) {
        List<Rol> roles = catalogosDAO.obtenerRoles();
        model.addAttribute("activeMenu", "catalogos");
        model.addAttribute("activeCatalogo", "roles");
        model.addAttribute("roles", roles);
        return "catalogos";
    }
}
