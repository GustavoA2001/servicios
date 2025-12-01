package com.servicios.login_service.service;


import com.servicios.login_service.dao.UsuarioDAO;
import com.servicios.login_service.model.Usuario;
import com.servicios.login_service.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UsuarioDAO usuarioDAO;
    private final JwtUtil jwtUtil;

    public AuthService(UsuarioDAO usuarioDAO, JwtUtil jwtUtil) {
        this.usuarioDAO = usuarioDAO;
        this.jwtUtil = jwtUtil;
    }

    public String login(String email, String rawPassword, String tipo) {

        System.out.println("=== LOGIN INICIADO ===");
        System.out.println("Tipo recibido: " + tipo);
        System.out.println("Email recibido: " + email);
    
        Usuario u;
    
        if (tipo.equalsIgnoreCase("CLIENTE")) {
    
            System.out.println("🔎 Buscando en tabla CLIENTE...");
            u = usuarioDAO.findClienteByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    
            System.out.println("✔ Cliente encontrado: " + u.getEmail());
            System.out.println("Rol asignado (cliente fijo): " + u.getRol());
    
        } else if (tipo.equalsIgnoreCase("EMPLEADO")) {
    
            System.out.println("🔎 Buscando en tabla EMPLEADO...");
            u = usuarioDAO.findEmpleadoByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    
            System.out.println("✔ Empleado encontrado: " + u.getEmail());
            System.out.println("Rol obtenido desde DB: " + u.getRol());
    
        } else {
            System.out.println("❌ Tipo inválido: " + tipo);
            throw new RuntimeException("Tipo inválido");
        }
    
        System.out.println("Validando contraseña...");
        if (!new BCryptPasswordEncoder().matches(rawPassword, u.getPassword())) {
            System.out.println("❌ CONTRASEÑA INCORRECTA");
            throw new RuntimeException("Credenciales inválidas");
        }
    
        System.out.println("✔ Contraseña correcta");
    
        String token = jwtUtil.generateToken(u.getEmail(), u.getRol());
    
        System.out.println("🔑 TOKEN GENERADO:");
        System.out.println(token);  // <-- Aquí verás el JWT completo
        
        System.out.println("🔐 Token generado con rol: " + u.getRol());
        System.out.println("=== LOGIN FINALIZADO ===");
    
        return token;
    }
}