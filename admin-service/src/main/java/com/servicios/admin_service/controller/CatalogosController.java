package com.servicios.admin_service.controller;

import com.servicios.admin_service.dao.*;
import com.servicios.admin_service.model.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PutMapping;


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

    @PostMapping("/distritos/{id}/estado")
    public String cambiarEstadoDistrito(@PathVariable int id,
            @RequestParam("nuevo") String nuevoEstado) {
        catalogosDAO.cambiarEstadoDistrito(id, nuevoEstado);
        return "redirect:/catalogos/distritos";
    }

    @PostMapping("/distritos/nuevo")
    @ResponseBody
    public ResponseEntity<Distrito> crearDistrito(@RequestBody Distrito nuevoDistrito) {
        Distrito distritoCreado = catalogosDAO.crearDistrito(nuevoDistrito);
        return ResponseEntity.ok(distritoCreado);
    }

    @GetMapping("/especialidades")
    public String mostrarEspecialidades(Model model) {
        List<Especialidad> especialidades = catalogosDAO.obtenerEspecialidades();
        model.addAttribute("activeMenu", "catalogos");
        model.addAttribute("activeCatalogo", "especialidades");
        model.addAttribute("especialidades", especialidades);
        return "catalogos";
    }

    @PostMapping("/especialidades/{id}/estado")
    public String cambiarEstadoEspecialidad(@PathVariable int id,
            @RequestParam("nuevo") String nuevoEstado) {
        catalogosDAO.cambiarEstadoEspecialidad(id, nuevoEstado);
        return "redirect:/catalogos/especialidades";
    }

    @PostMapping("/especialidades/nuevo")
    @ResponseBody
    public ResponseEntity<Especialidad> crearEspecialidad(@RequestBody Especialidad nuevaEspecialidad) {
        Especialidad especialidadCreada = catalogosDAO.crearEspecialidad(nuevaEspecialidad);
        return ResponseEntity.ok(especialidadCreada);
    }
    @PutMapping("/especialidades/{id}/editar")
    @ResponseBody
    public ResponseEntity<Especialidad> editarEspecialidad(
            @PathVariable int id,
            @RequestBody Especialidad especialidadEditada) {
    
        // Actualizamos directamente
        catalogosDAO.actualizarEspecialidad(id, especialidadEditada);
    
        // Opcional: devolver el objeto actualizado
        especialidadEditada.setEspecialidadID(id);
        especialidadEditada.setEstado_especialidad("Habilitado"); // o mantener el estado anterior si lo guardas
        return ResponseEntity.ok(especialidadEditada);
    }
    

    @GetMapping("/calificaciones")
    public String mostrarCalificaciones(Model model) {
        // List<Calificacion> calificaciones = catalogosDAO.obtenerCalificaciones();
        model.addAttribute("activeMenu", "catalogos");
        model.addAttribute("activeCatalogo", "calificaciones");
        // model.addAttribute("calificaciones", calificaciones);
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

    @PostMapping("/roles/{id}/estado")
    public String cambiarEstadoRol(@PathVariable int id,
            @RequestParam("nuevo") String nuevoEstado) {
        catalogosDAO.cambiarEstadoRol(id, nuevoEstado);
        return "redirect:/catalogos/roles";
    }

    @PostMapping("/roles/nuevo")
    @ResponseBody
    public ResponseEntity<Rol> crearRol(@RequestBody Rol nuevoRol) {
        Rol rolCreado = catalogosDAO.crearRol(nuevoRol);
        return ResponseEntity.ok(rolCreado);
    }
    @PutMapping("/roles/{id}/editar")
    @ResponseBody
    public ResponseEntity<Rol> editarRol(
            @PathVariable int id,
            @RequestBody Rol rolEditada) {
    
        // Actualizamos directamente
        catalogosDAO.actualizarRol(id, rolEditada);
    
        // Opcional: devolver el objeto actualizado
        rolEditada.setRolID(id);;
        rolEditada.setEstado_rol("Habilitado"); // o mantener el estado anterior si lo guardas
        return ResponseEntity.ok(rolEditada);
    }
    
}
