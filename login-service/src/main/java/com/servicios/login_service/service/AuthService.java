package com.servicios.login_service.service;


import com.servicios.login_service.dao.UsuarioDAO;
import com.servicios.login_service.model.Usuario;
import com.servicios.login_service.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UsuarioDAO usuarioDAO;
    private final JwtUtil jwtUtil;

    public AuthService(UsuarioDAO usuarioDAO, JwtUtil jwtUtil) {
        this.usuarioDAO = usuarioDAO;
        this.jwtUtil = jwtUtil;
    }

    public String login(String email, String rawPassword) {

        Optional<Usuario> cliente = usuarioDAO.findClienteByEmail(email);
        Optional<Usuario> empleado = usuarioDAO.findEmpleadoByEmail(email);
    
        // Usuario no existe
        Usuario u = cliente.orElseGet(() ->
                empleado.orElseThrow(() -> new RuntimeException("Usuario no encontrado"))
        );
    
        // Contraseña incorrecta
        if (!new BCryptPasswordEncoder().matches(rawPassword, u.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }
    
        // OK
        return jwtUtil.generateToken(u.getEmail(), u.getRol());
    }
}