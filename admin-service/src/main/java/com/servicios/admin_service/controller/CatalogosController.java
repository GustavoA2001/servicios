package com.servicios.admin_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
@RequestMapping("/catalogos")
public class CatalogosController {
    
    @GetMapping
    public String catalogosHTML(Model model) {

        model.addAttribute("activeMenu", "catalogos");
        return "catalogos";
    }
    
}
