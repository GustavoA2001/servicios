package com.servicios.admin_service.controller;

import com.servicios.admin_service.dao.*;
import com.servicios.admin_service.model.*;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

    private final UsuarioDAO usuarioDAO;

    public UsuariosController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    // Redirección al entrar a /usuarios
    @GetMapping
    public String UsuariosHTML(Model model) {
        model.addAttribute("activeMenu", "usuarios");
        return "redirect:/usuarios/clientes";
    }

    @GetMapping("/clientes")
    public String mostrarClientes(Model model) {
        List<Cliente> clientes = usuarioDAO.obtenerClientes();
        model.addAttribute("activeMenu", "usuarios");
        model.addAttribute("usuarios", clientes);
        model.addAttribute("tipo", "Cliente");
        model.addAttribute("contador", clientes.size()); 
        return "usuarios";
    }

    @GetMapping("/trabajadores")
    public String mostrarTrabajadores(Model model) {
        List<Empleado> trabajadores = usuarioDAO.obtenerEmpleadosPorRol(2);
        model.addAttribute("activeMenu", "usuarios");
        model.addAttribute("usuarios", trabajadores);
        model.addAttribute("tipo", "Trabajador");
        model.addAttribute("contador", trabajadores.size()); 
        return "usuarios";
    }

    @GetMapping("/administradores")
    public String mostrarAdministradores(Model model) {
        List<Empleado> administradores = usuarioDAO.obtenerEmpleadosPorRol(1);
        model.addAttribute("activeMenu", "usuarios");
        model.addAttribute("usuarios", administradores);
        model.addAttribute("tipo", "Administrador");
        model.addAttribute("contador", administradores.size()); 
        return "usuarios";
    }
    @GetMapping("/usuario/{tipo}/{id}")
    public String verUsuario(@PathVariable String tipo, @PathVariable Long id, Model model) {
        Usuario usuario;
    
        if ("Cliente".equalsIgnoreCase(tipo)) {
            usuario = usuarioDAO.obtenerClientePorId(id);
            tipo = "Cliente";  
        } else {
            usuario = usuarioDAO.obtenerEmpleadoPorId(id);
            tipo = "Empleado"; 
        }

        model.addAttribute("activeMenu", "usuarios");
        model.addAttribute("usuario", usuario);
        model.addAttribute("activePage", "usuario_info");
        model.addAttribute("tipo", tipo);
        return "usuarios";
    }
    
    
}
