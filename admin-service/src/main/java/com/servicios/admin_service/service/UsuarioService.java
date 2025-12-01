package com.servicios.admin_service.service;

import com.servicios.admin_service.dao.UsuarioDAO;
import com.servicios.admin_service.model.Cliente;
import com.servicios.admin_service.model.UsuarioRequest;
import com.servicios.admin_service.model.Empleado;
import com.servicios.admin_service.model.Usuario;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public List<Cliente> obtenerClientes() {
        return usuarioDAO.obtenerClientes();
    }

    public List<Empleado> obtenerEmpleadosPorRol(int rolID) {
        return usuarioDAO.obtenerEmpleadosPorRol(rolID);
    }

    public Cliente obtenerClientePorId(Long id) {
        return usuarioDAO.obtenerClientePorId(id);
    }

    public Empleado obtenerEmpleadoPorId(Long id) {
        return usuarioDAO.obtenerEmpleadoPorId(id);
    }

    /**
     * Registra un usuario en la base de datos y envía correo de notificación.
     * Si ocurre cualquier error, la transacción se revierte (rollback).
     */
    @Transactional(rollbackFor = Exception.class)
    public String registrarUsuario(UsuarioRequest usuarioRequest) throws Exception {
        try {
            LocalDateTime ahora = LocalDateTime.now();
            String rol = usuarioRequest.getRol().toLowerCase();

            if (rol.equals("cliente")) {
                Cliente c = new Cliente();
                c.setNombre(usuarioRequest.getNombre());
                c.setApellido(usuarioRequest.getApellido());
                c.setEmail(usuarioRequest.getEmail());
                
                String hash = new BCryptPasswordEncoder().encode(usuarioRequest.getPwd());
                c.setPwd(hash);
                
                c.setEstado("ACTIVO");
                c.setFechaRegistro(ahora);

                int filas = usuarioDAO.insertarCliente(c);
                if (filas == 0) throw new Exception("No se pudo insertar cliente en la base de datos");

            } else {
                Empleado e = new Empleado();
                e.setNombre(usuarioRequest.getNombre());
                e.setApellido(usuarioRequest.getApellido());
                e.setEmail(usuarioRequest.getEmail());
                
                String hash = new BCryptPasswordEncoder().encode(usuarioRequest.getPwd());
                e.setPwd(hash);

                e.setEstado("ACTIVO");
                e.setFechaRegistro(ahora);
                e.setRolID(rol.equals("administrador") ? 1 : 2);

                int filas = usuarioDAO.insertarEmpleado(e);
                if (filas == 0) throw new Exception("No se pudo insertar empleado en la base de datos");
            }

            //  Si se registró bien, enviamos notificación
            String respuesta = enviarNotificacionCorreo(usuarioRequest);
            return "Usuario registrado correctamente.\n" + respuesta;

        } catch (Exception ex) {
            throw new Exception("Error durante el registro: " + ex.getMessage());
        }
    }

    private String enviarNotificacionCorreo(UsuarioRequest usuario) throws Exception {
        String url = "http://localhost:8086/api/notificaciones/enviarCorreo";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String mensaje = String.format(
            "Hola %s %s,\n\nTu registro fue exitoso.\nRol: %s\nCorreo: %s\n\n¡Bienvenido a ADM!",
            usuario.getNombre(), usuario.getApellido(), usuario.getRol(), usuario.getEmail()
        );

        String json = String.format(
            "{\"destinatario\":\"%s\",\"asunto\":\"Registro exitoso\",\"mensaje\":\"%s\"}",
            usuario.getEmail(), mensaje.replace("\n", "\\n")
        );

        HttpEntity<String> request = new HttpEntity<>(json, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        return "Respuesta del servicio de notificaciones: " + response.getBody();
    }

    /**
     *  Este endpoint será llamado por notificaciones-service para confirmar envío
     */
    public void confirmarEnvioCorreo(String destinatario, String estado) {
        System.out.println(" Confirmación recibida desde notificaciones-service:");
        System.out.println("Destinatario: " + destinatario);
        System.out.println("Estado: " + estado);
    }
    
    public void actualizarUsuario(Usuario usuario) {
        if (usuario instanceof Cliente c) {
            usuarioDAO.actualizarCliente(c.getId(), c);
        } else if (usuario instanceof Empleado e) {
            usuarioDAO.actualizarEmpleado(e.getId(), e);
        }
    }

    @Transactional
    public void cambiarEstadoUsuario(String tipo, Long id, String nuevoEstado) throws Exception {
        // Estados válidos del sistema
        List<String> estadosValidos = List.of("Activo", "Inactivo", "Suspendido", "Eliminado");
    
        // Validamos que el estado solicitado sea correcto
        if (!estadosValidos.contains(nuevoEstado)) {
            throw new IllegalArgumentException("Estado no permitido: " + nuevoEstado);
        }
    
        int filasActualizadas = 0;
    
        // Según el tipo, actualizamos el estado
        if (tipo.equalsIgnoreCase("Cliente")) {
            filasActualizadas = usuarioDAO.cambiarEstadoCliente(id, nuevoEstado);
        } else if (tipo.equalsIgnoreCase("Empleado") || tipo.equalsIgnoreCase("Trabajador")) {
            filasActualizadas = usuarioDAO.cambiarEstadoEmpleado(id, nuevoEstado);
        } else {
            throw new IllegalArgumentException("Tipo de usuario no válido: " + tipo);
        }
    
        // Validamos que se haya actualizado
        if (filasActualizadas == 0) {
            throw new Exception("No se encontró el " + tipo.toLowerCase() + " con ID " + id);
        }
    }
    
        
    @Transactional
    public void eliminarUsuario(String tipo, Long id) throws Exception {
        if (tipo.equalsIgnoreCase("Cliente")) {
            usuarioDAO.eliminarCliente(id);
        } else {
            usuarioDAO.eliminarEmpleado(id);
        }
    }
}
