package com.servicios.admin_service.controller;

import com.servicios.admin_service.model.Servicio;
import com.servicios.admin_service.model.Pedido;
import com.servicios.admin_service.service.ServicioService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/servicios")
public class ServiciosController {

    private final ServicioService servicioService;

    public ServiciosController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @GetMapping
    public String listarServicios(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Servicio> servicios = servicioService.buscarServicios(search);

        model.addAttribute("activeMenu", "servicios");
        model.addAttribute("servicios", servicios);
        return "servicios";
    }

    @GetMapping("/{id}/clientes")
    public String obtenerClientesServicio(@PathVariable int id, Model model) {
        List<Pedido> pedidos = servicioService.obtenerClientesServicio(id);
        List<Servicio> servicios = servicioService.obtenerServicios();

        model.addAttribute("pedidos", pedidos);
        model.addAttribute("servicios", servicios);
        model.addAttribute("activePage", "servicio_cliente");
        model.addAttribute("activeMenu", "servicios");

        return "servicios";
    }
        // SUSPENDER SERVICIO
        @PostMapping("/{id}/suspender")
        public String suspenderServicio(@PathVariable int id) {
            servicioService.actualizarEstadoServicio(id, "Suspendido");
            return "redirect:/servicios";
        }
    
        // ACTIVAR SERVICIO
        @PostMapping("/{id}/activar")
        public String activarServicio(@PathVariable int id) {
            servicioService.actualizarEstadoServicio(id, "Activo");
            return "redirect:/servicios";
        }
    
        // ELIMINAR SERVICIO (marca como eliminado)
        @PostMapping("/{id}/eliminar")
        public String eliminarServicio(@PathVariable int id) {
            servicioService.actualizarEstadoServicio(id, "Eliminado");
            return "redirect:/servicios";
        }
}
