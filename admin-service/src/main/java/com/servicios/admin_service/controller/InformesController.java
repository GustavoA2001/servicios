package com.servicios.admin_service.controller;

import com.servicios.admin_service.client.PagosClient;
import com.servicios.admin_service.model.InformePagoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/informes")
public class InformesController {

    @Autowired
    private PagosClient pagosClient;

    @GetMapping
    public String informesForm(Model model) {
        // Traer informes completos desde pagos-service
        List<InformePagoDTO> informes = pagosClient.getInformes();

        model.addAttribute("informes", informes);
        model.addAttribute("activeMenu", "informes");

        return "informes"; // Vista Thymeleaf: informes.html
    }
}
