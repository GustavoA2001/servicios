package com.servicios.login_service.conttroller;

import com.servicios.login_service.Repository.*;
import com.servicios.login_service.model.Empleado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    // ...
    
    @PostMapping
    public Map<String, Object> login(@RequestBody Map<String, String> datos) {
        String email = datos.get("email");
        String password = datos.get("password");
    
        System.out.println(">>> Recibido email: [" + email + "] len=" + (email==null?0:email.length()));
        System.out.println(">>> Recibido password: [" + password + "] len=" + (password==null?0:password.length()));
        if (password != null) {
            System.out.println(">>> password bytes: " + Arrays.toString(password.getBytes(StandardCharsets.UTF_8)));
            System.out.print  (">>> password char codes: ");
            for (char c : password.toCharArray()) System.out.print((int)c + " ");
            System.out.println();
        }
    
        Empleado empleado = empleadoRepository.findByEmail(email).orElse(null);
        System.out.println(">>> Empleado encontrado?: " + (empleado!=null));
        if (empleado != null) {
            String dbPass = empleado.getPassword();
            System.out.println(">>> DB password raw: [" + dbPass + "] len=" + (dbPass==null?0:dbPass.length()));
            if (dbPass != null) {
                System.out.println(">>> DB password bytes: " + Arrays.toString(dbPass.getBytes(StandardCharsets.UTF_8)));
                System.out.print  (">>> DB password char codes: ");
                for (char c : dbPass.toCharArray()) System.out.print((int)c + " ");
                System.out.println();
            }
        }
    
        // comparación actual (igual que antes)
        if (empleado == null) return Map.of("status","error","message","Usuario no encontrado");
        if (!empleado.getPassword().equals(password)) {
            return Map.of("status","error","message","Contraseña incorrecta");
        }
    
        return Map.of("status","success","message","Inicio de sesión exitoso");
    }
    
}