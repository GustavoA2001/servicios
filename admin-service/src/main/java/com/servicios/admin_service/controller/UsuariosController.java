package com.servicios.admin_service.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.servicios.admin_service.model.*;
import com.servicios.admin_service.service.UsuarioService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    private UsuarioService usuarioService;

    // Redirección al entrar a /usuarios
    @GetMapping
    public String UsuariosHTML(Model model) {
        model.addAttribute("activeMenu", "usuarios");
        return "redirect:/usuarios/clientes";
    }

    @GetMapping("/clientes")
    public String listarClientes(Model model) {
        List<Cliente> clientes = usuarioService.obtenerClientes();
        model.addAttribute("usuarios", clientes);
        model.addAttribute("tipo", "Cliente");
        model.addAttribute("contador", clientes.size());
        model.addAttribute("activePage", "lista");
        model.addAttribute("activeMenu", "usuarios");

        return "usuarios";
    }

    @GetMapping("/trabajadores")
    public String listarTrabajadores(Model model) {
        List<Empleado> trabajadores = usuarioService.obtenerEmpleadosPorRol(2);
        model.addAttribute("usuarios", trabajadores);
        model.addAttribute("tipo", "Trabajador");
        model.addAttribute("contador", trabajadores.size());
        model.addAttribute("activePage", "lista");
        model.addAttribute("activeMenu", "usuarios");

        return "usuarios";
    }

    @GetMapping("/administradores")
    public String listarAdministradores(Model model) {
        List<Empleado> administradores = usuarioService.obtenerEmpleadosPorRol(1);
        model.addAttribute("usuarios", administradores);
        model.addAttribute("tipo", "Administrador");
        model.addAttribute("contador", administradores.size());
        model.addAttribute("activePage", "lista");
        model.addAttribute("activeMenu", "usuarios");

        return "usuarios";
    }

    @GetMapping("/usuario/{tipo}/{id}")
    public String obtenerUsuarioPorId(@PathVariable String tipo, @PathVariable Long id, Model model) {
        if (tipo.equalsIgnoreCase("Cliente")) {
            model.addAttribute("usuario", usuarioService.obtenerClientePorId(id));
        } else {
            model.addAttribute("usuario", usuarioService.obtenerEmpleadoPorId(id));
        }
        model.addAttribute("tipo", tipo);
        model.addAttribute("activePage", "usuario_info");
        model.addAttribute("activeMenu", "usuarios");

        return "usuarios";
    }

    // Endpoints REST
    @GetMapping("/api/clientes")
    @ResponseBody
    public ResponseEntity<List<Cliente>> apiClientes() {
        return ResponseEntity.ok(usuarioService.obtenerClientes());
    }

    @GetMapping("/api/empleados/{rolID}")
    @ResponseBody
    public ResponseEntity<List<Empleado>> apiEmpleados(@PathVariable int rolID) {
        return ResponseEntity.ok(usuarioService.obtenerEmpleadosPorRol(rolID));
    }

    @GetMapping("/api/cliente/{id}")
    @ResponseBody
    public ResponseEntity<Cliente> apiClientePorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerClientePorId(id));
    }

    @GetMapping("/api/empleado/{id}")
    @ResponseBody
    public ResponseEntity<Empleado> apiEmpleadoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerEmpleadoPorId(id));
    }

    @PostMapping("/registrar")
    @ResponseBody
    public ResponseEntity<String> registrarUsuario(@RequestBody UsuarioRequest nuevoUsuario) {
        try {
            usuarioService.registrarUsuario(nuevoUsuario);
            return ResponseEntity.ok("Usuario registrado exitosamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Error al registrar el usuario: " + e.getMessage());
        }
    }

    @PutMapping("/actualizar/{tipo}/{id}")
    @ResponseBody
    public ResponseEntity<String> actualizarUsuario(
            @PathVariable String tipo,
            @PathVariable Long id,
            @RequestBody Map<String, Object> body) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    
            Usuario usuarioExistente;
            Usuario usuarioParcial;
    
            switch (tipo.toLowerCase()) {
                case "cliente" -> {
                    usuarioExistente = usuarioService.obtenerClientePorId(id);
                    if (usuarioExistente == null)
                        return ResponseEntity.badRequest().body("Cliente no encontrado");
    
                    usuarioParcial = mapper.convertValue(body, Cliente.class);
                }
                case "empleado", "trabajador", "administrador" -> {
                    usuarioExistente = usuarioService.obtenerEmpleadoPorId(id);
                    if (usuarioExistente == null)
                        return ResponseEntity.badRequest().body("Empleado no encontrado");
    
                    Empleado parcial = mapper.convertValue(body, Empleado.class);
    
                    // Si el tipo es administrador o trabajador, asignamos el rol explícitamente
                    if (tipo.equalsIgnoreCase("administrador")) parcial.setRolID(1);
                    if (tipo.equalsIgnoreCase("trabajador")) parcial.setRolID(2);
    
                    usuarioParcial = parcial;
                }
                default -> {
                    return ResponseEntity.badRequest().body("Tipo de usuario no reconocido");
                }
            }
    
            // Combinar datos del existente y parcial
            Usuario usuarioFinal = combinarUsuarios(usuarioExistente, usuarioParcial);
    
            // Persistir cambios
            usuarioService.actualizarUsuario(usuarioFinal);
    
            return ResponseEntity.ok("Usuario actualizado correctamente");
    
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Error al actualizar el usuario: " + e.getMessage());
        }
    }
    
    
    private Usuario combinarUsuarios(Usuario original, Usuario nuevo) {
        if (original instanceof Cliente o && nuevo instanceof Cliente n) {
            if (n.getNombre() != null) o.setNombre(n.getNombre());
            if (n.getApellido() != null) o.setApellido(n.getApellido());
            if (n.getEmail() != null) o.setEmail(n.getEmail());
            if (n.getPwd() != null) o.setPwd(n.getPwd());
            if (n.getDNI() != null) o.setDNI(n.getDNI());
            if (n.getFotito() != null) o.setFotito(n.getFotito());
            if (n.getEstado() != null) o.setEstado(n.getEstado());
            return o;
        }
    
        if (original instanceof Empleado o && nuevo instanceof Empleado n) {
            if (n.getNombre() != null) o.setNombre(n.getNombre());
            if (n.getApellido() != null) o.setApellido(n.getApellido());
            if (n.getEmail() != null) o.setEmail(n.getEmail());
            if (n.getPwd() != null) o.setPwd(n.getPwd());
            if (n.getDNI() != null) o.setDNI(n.getDNI());
            if (n.getTelefono() != null) o.setTelefono(n.getTelefono());
            if (n.getEstado() != null) o.setEstado(n.getEstado());
            if (n.getRolID() != 0) o.setRolID(n.getRolID());
            if (n.getFotito() != null) o.setFotito(n.getFotito());
            if (n.getUbicacionPartida() != null) o.setUbicacionPartida(n.getUbicacionPartida());
            return o;
        }
    
        return original;
    }


    @PutMapping("/estado/{tipo}/{id}")
    @ResponseBody
    public ResponseEntity<String> cambiarEstadoUsuario(
            @PathVariable String tipo,
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
    
        try {
            String nuevoEstado = body.get("estado");
            usuarioService.cambiarEstadoUsuario(tipo, id, nuevoEstado);
            return ResponseEntity.ok("Estado cambiado a " + nuevoEstado + " correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el estado: " + e.getMessage());
        }
    }
    
    

}
