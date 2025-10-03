package com.servicios.admin_service.controller;

import com.servicios.admin_service.dao.ServicioDAO;
import com.servicios.admin_service.model.Pedido;
import com.servicios.admin_service.model.Servicio;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@RequestMapping("/servicios")
public class ServiciosController {

    private final ServicioDAO servicioDAO;

    public ServiciosController(ServicioDAO servicioDAO) {
        this.servicioDAO = servicioDAO;
    }

    @GetMapping
    public String ServiciosHTML(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Servicio> servicios;

        // Si hay un término de búsqueda, usamos el método buscarServicios
        if (search != null && !search.trim().isEmpty()) {
            servicios = servicioDAO.buscarServicios(search);
        } else {
            servicios = servicioDAO.obtenerServicios();  // Obtener todos si no hay búsqueda
        }

        model.addAttribute("activeMenu", "servicios");
        model.addAttribute("servicios", servicios);
        return "servicios";
    }

    @GetMapping("/{id}/clientes/html")
    public String obtenerClientesServicioHTML(@PathVariable int id, Model model) {
        List<Pedido> pedidos = servicioDAO.obtenerClientesServicio(id);
        model.addAttribute("pedidos", pedidos);
        model.addAttribute("activePage", "servicio_cliente");
        List<Servicio> servicios = servicioDAO.obtenerServicios();
        model.addAttribute("servicios", servicios);

        model.addAttribute("activeMenu", "servicios");

        return "servicios";
    }
}

