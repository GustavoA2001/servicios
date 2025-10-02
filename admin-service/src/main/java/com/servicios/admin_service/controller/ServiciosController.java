package com.servicios.admin_service.controller;

import com.servicios.admin_service.dao.ServicioDAO;
import com.servicios.admin_service.model.Pedido;
import com.servicios.admin_service.model.Servicio;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/servicios")
public class ServiciosController {

    private final ServicioDAO servicioDAO;

    public ServiciosController(ServicioDAO servicioDAO) {
        this.servicioDAO = servicioDAO;
    }

    @GetMapping
    public String ServiciosHTML(Model model) {
        List<Servicio> servicios = servicioDAO.obtenerServicios();
        model.addAttribute("activeMenu", "servicios");
        model.addAttribute("servicios", servicios);

        return "servicios";
    }
    

    @GetMapping("/{id}/clientes")
    @ResponseBody
    public List<Pedido> obtenerClientesServicio(@PathVariable int id, Model model) {
        model.addAttribute("activePage", "servicio_cliente");
        return servicioDAO.obtenerClientesServicio(id);
    } 

}
