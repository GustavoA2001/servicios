package com.servicios.admin_service.service;

import com.servicios.admin_service.dao.UsuarioDAO;
import com.servicios.admin_service.model.Cliente;
import com.servicios.admin_service.model.UsuarioRequest;
import com.servicios.admin_service.model.Empleado;
import com.servicios.admin_service.model.Usuario;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
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

    // Obtiene la lista de clientes desde la base de datos
    public List<Cliente> obtenerClientes() {
        return usuarioDAO.obtenerClientes();
    }

    // Obtiene empleados filtrando por un rol específico
    public List<Empleado> obtenerEmpleadosPorRol(int rolID) {
        return usuarioDAO.obtenerEmpleadosPorRol(rolID);
    }

    // Obtiene un cliente por su ID
    public Cliente obtenerClientePorId(Long id) {
        return usuarioDAO.obtenerClientePorId(id);
    }

    // Obtiene un empleado por su ID
    public Empleado obtenerEmpleadoPorId(Long id) {
        return usuarioDAO.obtenerEmpleadoPorId(id);
    }

    /**
     * Registra un usuario y envía correo.
     * La transacción se revierte si ocurre cualquier error.
     */
    @Transactional(rollbackFor = Exception.class)
    public String registrarUsuario(UsuarioRequest usuarioRequest) throws Exception {
        try {
            LocalDateTime ahora = LocalDateTime.now();
            String rol = usuarioRequest.getRol().toLowerCase(); // se normaliza el rol recibido

            // Si el rol es CLIENTE, se crea un objeto Cliente
            if (rol.equals("cliente")) {
                Cliente c = new Cliente();
                c.setNombre(usuarioRequest.getNombre());
                c.setApellido(usuarioRequest.getApellido());
                c.setEmail(usuarioRequest.getEmail());
                c.setPwd(new BCryptPasswordEncoder().encode(usuarioRequest.getPwd())); // encriptación del password
                c.setEstado("Activo");
                c.setFechaRegistro(ahora);

                int filas = usuarioDAO.insertarCliente(c); // inserción en la BD
                if (filas == 0) throw new Exception("No se pudo insertar cliente en la base de datos");

            // Caso contrario, se trata como empleado
            } else {
                Empleado e = new Empleado();
                e.setNombre(usuarioRequest.getNombre());
                e.setApellido(usuarioRequest.getApellido());
                e.setEmail(usuarioRequest.getEmail());
                e.setPwd(new BCryptPasswordEncoder().encode(usuarioRequest.getPwd()));
                e.setEstado("Activo");
                e.setFechaRegistro(ahora);
                
                // Si el rol es "administrador", se asigna ID 1; caso contrario, ID 2
                e.setRolID(rol.equals("administrador") ? 1 : 2);

                int filas = usuarioDAO.insertarEmpleado(e);
                if (filas == 0) throw new Exception("No se pudo insertar empleado en la base de datos");
            }

            // Envía un correo de bienvenida llamando al microservicio de notificaciones
            enviarNotificacionCorreo(usuarioRequest);

            return "Usuario registrado correctamente";

        } catch (Exception ex) {
            // Cualquier error genera rollback
            throw new Exception("Error durante el registro: " + ex.getMessage());
        }
    }

    /**
     * Envía solicitud al microservicio de notificaciones para enviar un correo.
     */
    private void enviarNotificacionCorreo(UsuarioRequest usuario) throws Exception {
        String url = "http://localhost:8086/api/notificaciones/enviarCorreo";
        RestTemplate restTemplate = new RestTemplate(); // cliente HTTP

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // enviamos JSON

        // Mensaje de bienvenida personalizado
        String mensaje = String.format(
            "Hola %s %s,\n\nTu registro fue exitoso.\nRol: %s\nCorreo: %s\n\n¡Bienvenido!",
            usuario.getNombre(), usuario.getApellido(), usuario.getRol(), usuario.getEmail()
        );

        // Construcción manual del JSON para el microservicio
        String json = String.format(
            "{\"destinatario\":\"%s\",\"asunto\":\"Registro exitoso\",\"mensaje\":\"%s\"}",
            usuario.getEmail(), mensaje.replace("\n", "\\n") // se escapan los saltos de línea
        );

        // Se envía el JSON con headers vía POST
        HttpEntity<String> request = new HttpEntity<>(json, headers);
        restTemplate.postForEntity(url, request, String.class);
    }

    /**
     * Método invocado por notificaciones-service para confirmar que un correo fue enviado.
     */
    public void confirmarEnvioCorreo(String destinatario, String estado) {
        System.out.println(" Confirmación recibida desde notificaciones-service:");
        System.out.println("Destinatario: " + destinatario);
        System.out.println("Estado: " + estado);
    }

    /**
     * Actualiza un usuario dependiendo si es Cliente o Empleado.
     */
    public void actualizarUsuario(Usuario usuario) {
        if (usuario instanceof Cliente c) {
            usuarioDAO.actualizarCliente(c.getId(), c);
        } else if (usuario instanceof Empleado e) {
            usuarioDAO.actualizarEmpleado(e.getId(), e);
        }
    }

    /**
     * Cambia el estado de un usuario. Solo acepta estados permitidos.
     */
    @Transactional
    public void cambiarEstadoUsuario(String tipo, Long id, String nuevoEstado) throws Exception {
        // Estados válidos
        List<String> estadosValidos = List.of("Activo", "Inactivo", "Suspendido", "Eliminado");

        // Validación del estado solicitado
        if (!estadosValidos.contains(nuevoEstado)) {
            throw new IllegalArgumentException("Estado no permitido: " + nuevoEstado);
        }

        int filasActualizadas = 0;

        // Se determina si el usuario es cliente o empleado
        if (tipo.equalsIgnoreCase("Cliente")) {
            filasActualizadas = usuarioDAO.cambiarEstadoCliente(id, nuevoEstado);
        } else if (
            tipo.equalsIgnoreCase("Empleado") || 
            tipo.equalsIgnoreCase("Trabajador") || 
            tipo.equalsIgnoreCase("Administrador")
        ) {
            filasActualizadas = usuarioDAO.cambiarEstadoEmpleado(id, nuevoEstado);
        } else {
            throw new IllegalArgumentException("Tipo de usuario no válido: " + tipo);
        }

        // Si no se pudo actualizar, se lanza excepción
        if (filasActualizadas == 0) {
            throw new Exception("No se encontró el " + tipo.toLowerCase() + " con ID " + id);
        }
    }

    /**
     * Elimina un usuario definitivamente, según su tipo.
     */
    @Transactional
    public void eliminarUsuario(String tipo, Long id) throws Exception {
        if (tipo.equalsIgnoreCase("Cliente")) {
            usuarioDAO.eliminarCliente(id);
        } else {
            usuarioDAO.eliminarEmpleado(id);
        }
    }
}
