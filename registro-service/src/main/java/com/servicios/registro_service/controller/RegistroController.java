package com.servicios.registro_service.controller;

import com.servicios.registro_service.model.Usuario;
import com.servicios.registro_service.service.RegistroService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/auth") // Prefijo común, para la ruta debe ir antes para acceder
public class RegistroController {

    private static final Logger logger = LoggerFactory.getLogger(RegistroController.class);

    private final RegistroService registroService;

    public RegistroController(RegistroService registroService) {
        this.registroService = registroService;
    }


    @GetMapping("/registro")
    public String registerPage() {
        logger.info("Mostrando página de registro");
        return "registro";
    }

    /**
     * POST /auth/registrar
     * Endpoint REST que recibe un JSON con los datos del usuario:
     * 
     */
    @PostMapping("/registrar")
    @ResponseBody
    public ResponseEntity<String> registrarUsuario(@RequestBody Usuario usuario) {

        System.out.println("=== Iniciando registro de usuario ===");
        System.out.println("Datos recibidos:");
        System.out.println("Nombre: " + usuario.getNombre());
        System.out.println("Apellido: " + usuario.getApellido());
        System.out.println("Email: " + usuario.getEmail());
        System.out.println("Password: " + usuario.getPassword());
        System.out.println("DNI: " + usuario.getDni());
        System.out.println("Rol: " + usuario.getRol());

        try {
            // Diferenciamos si es cliente o empleado
            if ("CLIENTE".equalsIgnoreCase(usuario.getRol())) {
                System.out.println("Registrando CLIENTE...");

                registroService.registerCliente(
                        usuario.getNombre(),
                        usuario.getApellido(),
                        usuario.getEmail(),
                        usuario.getPassword(),
                        usuario.getDni()
                );

            } else {
                System.out.println("Registrando EMPLEADO...");
                int rolId = mapRolToId(usuario.getRol()); // Convertimos el rol a ID
                System.out.println("Rol ID asignado: " + rolId);

                registroService.registerEmpleado(
                        usuario.getNombre(),
                        usuario.getApellido(),
                        usuario.getEmail(),
                        usuario.getPassword(),
                        usuario.getDni(),
                        rolId
                );
            }

            System.out.println("Usuario registrado correctamente.");
            return ResponseEntity.ok("Usuario registrado correctamente");

        } catch (Exception e) {
            System.out.println("ERROR al registrar usuario: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Error al registrar el usuario: " + e.getMessage());
        }
    }

    /**
     * Convierte un nombre de rol a su ID para persistencia en BD.
     */
    private int mapRolToId(String rol) {
        logger.info("Mapeando rol: {}", rol);

        switch (rol.toUpperCase()) {
            case "ADMIN":
                return 1;
            case "EMPLEADO":
                return 2;
            case "CLIENTE":
                return 3;
            default:
                logger.error("Rol inválido: {}", rol);
                throw new IllegalArgumentException("Rol no válido: " + rol);
        }
    }
}
