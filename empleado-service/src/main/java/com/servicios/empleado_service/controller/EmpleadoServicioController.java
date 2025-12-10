package com.servicios.empleado_service.controller;

import com.servicios.empleado_service.model.Servicio;
import com.servicios.empleado_service.service.ServicioService;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/empleado/servicios")
public class EmpleadoServicioController {

    private final ServicioService servicioService;

    public EmpleadoServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @GetMapping
    public String viewServicios(HttpServletRequest request, Model model) {
        Integer empleadoId = (Integer) request.getAttribute("empleadoId");
        model.addAttribute("servicios", servicioService.listByEmpleado(empleadoId));
        model.addAttribute("empleadoId", empleadoId);
        model.addAttribute("nuevoServicio", new Servicio());
        return "empleado/servicios";
    }

    @PostMapping(params = "action=create")
    public String create(@Valid @ModelAttribute("nuevoServicio") Servicio servicio,
                         HttpServletRequest request) {
        Integer empleadoId = (Integer) request.getAttribute("empleadoId");
        servicioService.saveAndAssign(servicio, empleadoId);
        return "redirect:/empleado/servicios";
    }

    @PostMapping(params = "action=update")
    public String update(@ModelAttribute("nuevoServicio") Servicio servicio,
                         HttpServletRequest request) {
        Integer empleadoId = (Integer) request.getAttribute("empleadoId");
        servicioService.save(servicio);
        return "redirect:/empleado/servicios";
    }

    @PostMapping(params = "action=delete")
    public String delete(@RequestParam("servicioID") Integer servicioID,
                         HttpServletRequest request) {
        Integer empleadoId = (Integer) request.getAttribute("empleadoId");
        if (empleadoId != null && empleadoId > 0) {
            servicioService.removeAssignment(empleadoId, servicioID);
        } else {
            servicioService.delete(servicioID);
        }
        return "redirect:/empleado/servicios";
    }
}
