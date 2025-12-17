package com.servicios.admin_service.service;

import com.servicios.admin_service.client.NotificacionesClient;
import com.servicios.admin_service.dao.UsuarioDAO;
import com.servicios.admin_service.model.Cliente;
import com.servicios.admin_service.model.EmailRequest;
import com.servicios.admin_service.model.UsuarioRequest;
import com.servicios.admin_service.model.Empleado;
import com.servicios.admin_service.model.Usuario;
import com.servicios.admin_service.model.AuditoriaDTO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioDAO usuarioDAO;
    private final NotificacionesClient notificacionesClient;
    private final MensajeService mensajeService;
    private final AuditoriaService auditoriaService;

    public UsuarioService(UsuarioDAO usuarioDAO, NotificacionesClient notificacionesClient,
            MensajeService mensajeService, AuditoriaService auditoriaService ) {
        this.usuarioDAO = usuarioDAO;
        this.notificacionesClient = notificacionesClient;
        this.mensajeService = mensajeService;
        this.auditoriaService = auditoriaService;
    }

    // Obtiene la lista de clientes desde la base de datos
    public List<Cliente> obtenerClientes() {    return usuarioDAO.obtenerClientes();    }
    // Obtiene empleados filtrando por un rol específico
    public List<Empleado> obtenerEmpleadosPorRol(int rolID) {    return usuarioDAO.obtenerEmpleadosPorRol(rolID);    }
    // Obtiene un cliente por su ID
    public Cliente obtenerClientePorId(Long id) {    return usuarioDAO.obtenerClientePorId(id);    }
    // Obtiene un empleado por su ID
    public Empleado obtenerEmpleadoPorId(Long id) {    return usuarioDAO.obtenerEmpleadoPorId(id);    }

    /**
     * Registra un usuario y envía correo.
     * La transacción se revierte si ocurre cualquier error.
     */
    @Transactional(rollbackFor = Exception.class)
    public String registrarUsuario(UsuarioRequest usuarioRequest, 
        String actorRol, 
        Long actorId,
        String endpoint,
        String httpMethod) throws Exception {

        System.out.println("INGRESO AL METODO REGISTRAR EN SERVICE");
        try {
            LocalDateTime ahora = LocalDateTime.now();
            String rol = usuarioRequest.getRol().toLowerCase(); // se normaliza el rol recibido

            Usuario nuevo;
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
                if (filas == 0)
                    throw new Exception("No se pudo insertar cliente en la base de datos");

                nuevo = c;
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
                if (filas == 0)
                    throw new Exception("No se pudo insertar empleado en la base de datos");

                nuevo = e;
            }

            // Envía un correo de bienvenida llamando al microservicio de notificaciones
            enviarNotificacionCorreo(usuarioRequest);

            // Auditoría: solo después porque es un alta 
            auditarCambioUsuario( 
                httpMethod + " Registrar usuario", 
                rol.equals("cliente") ? "Cliente" : "Empleado", 
                nuevo.getId(), null, // antes es null 
                nuevo, // después con datos 
                actorRol, 
                actorId, 
                endpoint, 
                false // extras no aplican 
            );
            
            System.out.println("=======");
            System.out.println(nuevo);
            System.out.println("=======");

            System.out.println("SALE EXITO AL METODO REGISTRAR EN SERVICE");
            
            return "Usuario registrado correctamente";

        } catch (Exception ex) {
            // Cualquier error genera rollback
            throw new Exception("Error durante el registro: " + ex.getMessage());
        }
    }

    /**
     * Envía solicitud al microservicio de notificaciones para enviar un correo.
     */
    private void enviarNotificacionCorreo(UsuarioRequest usuario) {
        String mensaje = String.format(
                "Hola %s %s,\n\nTu registro fue exitoso.\nRol: %s\nCorreo: %s\n\n¡Bienvenido!",
                usuario.getNombre(), usuario.getApellido(), usuario.getRol(), usuario.getEmail());

        EmailRequest email = new EmailRequest(
                usuario.getEmail(),
                "Registro exitoso",
                mensaje);

        try {
            // Solo enviamos la solicitud, no guardamos mensaje aquí
            notificacionesClient.enviarCorreo(email);
        } catch (Exception e) {
            mensajeService.guardarMensaje("Error al enviar correo: " + e.getMessage());
        }
    }

    /**
     * Método invocado por notificaciones-service para confirmar que un correo fue
     * enviado.
     */
    public void confirmarEnvioCorreo(String destinatario, String estado) {
        String msg = "Correo enviado a " + destinatario + " - Estado: " + estado;
        System.out.println("[UsuarioService] " + msg);
    
        // Solo confirma en lógica interna, no guarda en MensajeService
        // mensajeService.guardarMensaje(msg);
    }
    

    /**
     * Actualiza un usuario dependiendo si es Cliente o Empleado.
     */
    public void actualizarUsuario(Usuario usuario, 
                                    String actorRol, 
                                    Long actorId,
                                    String endpoint,
                                    String httpMethod) {

        System.out.println("INGRESO AL METODO ACTUALIZAR EN SERVICE");

        Usuario antes = (usuario instanceof Cliente)
            ? usuarioDAO.obtenerClientePorId(usuario.getId())
            : usuarioDAO.obtenerEmpleadoPorId(usuario.getId());

        if (usuario instanceof Cliente c) {
            usuarioDAO.actualizarCliente(c.getId(), c);
        } else if (usuario instanceof Empleado e) {
            usuarioDAO.actualizarEmpleado(e.getId(), e);
        }

        Usuario despues = (usuario instanceof Cliente)
            ? usuarioDAO.obtenerClientePorId(usuario.getId())
            : usuarioDAO.obtenerEmpleadoPorId(usuario.getId());

        System.out.println(antes);
        System.out.println(despues);

        // Auditoría
        auditarCambioUsuario( 
            httpMethod + " Actualizar usuario", 
            usuario instanceof Cliente ? "Cliente" : "Empleado", 
            usuario.getId(), 
            antes, 
            despues, 
            actorRol, 
            actorId, 
            endpoint, 
            true // incluir extras 
        );

        System.out.println("SALIO AL METODO ACTUALIZAR EN SERVICE");

    }

    /**
     * Cambia el estado de un usuario. Solo acepta estados permitidos.
     */
    @Transactional
    public void cambiarEstadoUsuario(String tipo, 
                                        Long id, 
                                        String nuevoEstado, 
                                        String actorRol, 
                                        Long actorId,
                                        String endpoint,
                                        String httpMethod) throws Exception {

        System.out.println("INGRESO AL METODO ESTADO EN SERVICE");

        // Estados válidos
        List<String> estadosValidos = List.of("Activo", "Inactivo", "Suspendido", "Eliminado");

        // Validación del estado solicitado
        if (!estadosValidos.contains(nuevoEstado)) {
            throw new IllegalArgumentException("Estado no permitido: " + nuevoEstado);
        }

        Usuario antes = tipo.equalsIgnoreCase("Cliente")
        ? usuarioDAO.obtenerClientePorId(id)       
        : usuarioDAO.obtenerEmpleadoPorId(id);   
    
        //int filasActualizadas = 0;
        int filasActualizadas;

        // Se determina si el usuario es cliente o empleado
        if (tipo.equalsIgnoreCase("Cliente")) {
            filasActualizadas = usuarioDAO.cambiarEstadoCliente(id, nuevoEstado);
        } else if (tipo.equalsIgnoreCase("Empleado") ||
                tipo.equalsIgnoreCase("Trabajador") ||
                tipo.equalsIgnoreCase("Administrador")) {
            filasActualizadas = usuarioDAO.cambiarEstadoEmpleado(id, nuevoEstado);
        } else {
            throw new IllegalArgumentException("Tipo de usuario no válido: " + tipo);
        }

        // Si no se pudo actualizar, se lanza excepción
        if (filasActualizadas == 0) {
            throw new Exception("No se encontró el " + tipo.toLowerCase() + " con ID " + id);
        }

        Usuario despues = tipo.equalsIgnoreCase("Cliente") 
            ? usuarioDAO.obtenerClientePorId(id) 
            : usuarioDAO.obtenerEmpleadoPorId(id);

            auditarCambioUsuario( 
                httpMethod + " Cambiar estado", 
                tipo, 
                id, 
                antes, 
                despues, 
                actorRol, 
                actorId, 
                endpoint, 
                false // extras no aplican                 
            );
     
        System.out.println("SALIO AL METODO ESTADO EN SERVICE");
                
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

    // ============================ 
    // Método privado para auditoría
    // ============================ 
    private void auditarCambioUsuario(String accion, 
                                        String tipo, 
                                        Long entidadId, 
                                        Usuario antes, 
                                        Usuario despues, 
                                        String actorRol, 
                                        Long actorId, 
                                        String endpoint, 
                                        boolean incluirExtras) { 
        AuditoriaDTO antesDTO = (antes != null) ? new AuditoriaDTO(antes, incluirExtras) : null; 
        AuditoriaDTO despuesDTO = (despues != null) ? new AuditoriaDTO(despues, incluirExtras) : null; 
        auditoriaService.registrarCambio( "admin-service", 
        accion, 
        tipo, 
        entidadId, 
        antesDTO, 
        despuesDTO, 
        actorRol, 
        actorId, 
        endpoint ); 
    }
}
