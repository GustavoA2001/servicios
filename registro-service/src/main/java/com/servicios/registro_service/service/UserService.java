package com.servicios.registro_service.service;

import com.servicios.registro_service.model.User;
import com.servicios.registro_service.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String username, String email, String rawPassword) {
        if (repo.existsByUsername(username)) {
            throw new RuntimeException("Usuario ya existe");
        }
        User u = new User();
        u.setUsername(username);
        u.setEmail(email);
        u.setPassword(passwordEncoder.encode(rawPassword));
        u.setRoles("ROLE_USER");
        return repo.save(u);
    }
}
