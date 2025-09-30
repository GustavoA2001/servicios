package com.servicios.admin_service.controller;

import com.servicios.admin_service.service.DbConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DbTestController {

    @Autowired
    private DbConnectionService dbConnectionService;

    @GetMapping("/testdb")
    public String testDbConnection(Model model) {
        // Llamamos al servicio para probar la conexión
        String result = dbConnectionService.testConnection();
        
        // Pasamos el resultado al modelo
        model.addAttribute("message", result);
        
        // Retornamos la vista 'testdb.html'
        return "bd";
    }
}

