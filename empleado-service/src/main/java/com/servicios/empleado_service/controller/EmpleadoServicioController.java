package com.servicios.empleado_service.controller;

import com.servicios.empleado_service.model.Servicio;
import com.servicios.empleado_service.model.ServicioDTO;
import com.servicios.empleado_service.service.CatalogoService;
import com.servicios.empleado_service.service.ServicioService;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/empleado/servicios")
public class EmpleadoServicioController {

    private final ServicioService servicioService;
    private final CatalogoService catalogoService;

    public EmpleadoServicioController(ServicioService servicioService,
                                      CatalogoService catalogoService) {
        this.servicioService = servicioService;
        this.catalogoService = catalogoService;
    }

    @GetMapping
    public String viewServicios(HttpServletRequest request, Model model) {
        Integer empleadoId = (Integer) request.getAttribute("empleadoId");

        model.addAttribute("servicios", servicioService.listByEmpleado(empleadoId));
        model.addAttribute("empleadoId", empleadoId);
        model.addAttribute("nuevoServicio", new Servicio());

        // Cargar categorías y especialidades desde la BD
        model.addAttribute("categorias", catalogoService.listarCategorias());
        model.addAttribute("especialidades", catalogoService.listarEspecialidades());

        return "empleado/servicios";
    }

    @PostMapping(params = "action=update")
    public String update(@ModelAttribute("nuevoServicio") Servicio servicio,
                         HttpServletRequest request) {

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

    
    @PostMapping(params = "action=create")
    public String create(@ModelAttribute("nuevoServicio") ServicioDTO dto,
                         HttpServletRequest request) {
    
        try {
            Integer empleadoId = (Integer) request.getAttribute("empleadoId");
            dto.setEmpleadoId(empleadoId);
    
            // CREACIÓN SOLO JDBC
            servicioService.crearServicioDTO(dto);
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return "redirect:/empleado/servicios";
    }    
    
}
