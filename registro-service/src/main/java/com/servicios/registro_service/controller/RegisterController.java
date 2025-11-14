package com.servicios.registro_service.controller;

import com.servicios.registro_service.model.User;
import com.servicios.registro_service.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String email = body.get("email");
        String password = body.get("password");
        User saved = userService.register(username, email, password);
        saved.setPassword(null);
        return ResponseEntity.ok(saved);
    }
}
