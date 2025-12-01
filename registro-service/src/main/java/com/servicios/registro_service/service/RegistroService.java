package com.servicios.registro_service.service;

import com.servicios.registro_service.dao.UsuarioDAO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class RegistroService {

    private final UsuarioDAO usuarioDAO;

    public RegistroService(UsuarioDAO usuarioDAO) { this.usuarioDAO = usuarioDAO; }

    public void registerCliente(String nombre, String apellido, String email, String rawPassword, String dni) {
        System.out.println("SERVICE: Registrando Cliente " + email);
    
        String hash = new BCryptPasswordEncoder().encode(rawPassword);
    
        System.out.println("SERVICE: Password hasheado -> " + hash);
    
        usuarioDAO.insertCliente(nombre, apellido, email, hash, dni);
    }
    
    public void registerEmpleado(String nombre, String apellido, String email, String rawPassword, String dni, int rolId) {
        System.out.println("SERVICE: Registrando Empleado " + email + " con rol " + rolId);
    
        String hash = new BCryptPasswordEncoder().encode(rawPassword);
    
        System.out.println("SERVICE: Password hasheado -> " + hash);
    
        usuarioDAO.insertEmpleado(nombre, apellido, email, hash, dni, rolId);
    }
    
}