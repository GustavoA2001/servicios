package com.servicios.empleado_service.controller;

import com.servicios.empleado_service.model.Servicio;
import com.servicios.empleado_service.service.ServicioService;
import jakarta.validation.Valid;
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
    public String viewServicios(@RequestParam(value = "empleadoId", required = false) Integer empleadoId, Model model) {
        model.addAttribute("servicios", servicioService.listByEmpleado(empleadoId));
        model.addAttribute("empleadoId", empleadoId == null ? 0 : empleadoId);
        model.addAttribute("nuevoServicio", new Servicio());
        return "empleado/servicios";
    }

    @PostMapping(params = "action=create")
    public String create(@Valid @ModelAttribute("nuevoServicio") Servicio servicio,
                         @RequestParam(value="empleadoId", required=false) Integer empleadoId) {
        servicioService.saveAndAssign(servicio, empleadoId);
        return "redirect:/empleado/servicios?empleadoId=" + (empleadoId == null ? 0 : empleadoId);
    }

    @PostMapping(params = "action=update")
    public String update(@ModelAttribute("nuevoServicio") Servicio servicio,
                         @RequestParam(value="empleadoId", required=false) Integer empleadoId) {
        servicioService.save(servicio);
        return "redirect:/empleado/servicios?empleadoId=" + (empleadoId == null ? 0 : empleadoId);
    }

    @PostMapping(params = "action=delete")
    public String delete(@RequestParam("servicioID") Integer servicioID,
                         @RequestParam(value="empleadoId", required=false) Integer empleadoId) {

        if (empleadoId != null && empleadoId > 0) {
            servicioService.removeAssignment(empleadoId, servicioID);
        } else {
            servicioService.delete(servicioID);
        }
        return "redirect:/empleado/servicios?empleadoId=" + (empleadoId == null ? 0 : empleadoId);
    }
}
