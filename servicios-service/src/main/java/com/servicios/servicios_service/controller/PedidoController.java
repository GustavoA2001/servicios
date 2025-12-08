package com.servicios.servicios_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    @GetMapping("/nuevo/{servicioId}")
    public String mostrarFormulario(@PathVariable Integer servicioId, Model model) {
        // Por ahora datos estáticos, luego se integrará con la BD
        model.addAttribute("servicioId", servicioId);
        model.addAttribute("tituloServicio", "Servicio Técnico de Electricidad");
        model.addAttribute("precioEstimado", 150.00);
        return "form_pedido";
    }

    @PostMapping("/guardar")
    public String guardarPedido(@RequestParam String direccion,
                                @RequestParam String fecha,
                                @RequestParam String dias,
                                @RequestParam String observaciones,
                                @RequestParam Integer servicioId,
                                Model model) {
        // Por ahora solo mostramos lo que se envió
        model.addAttribute("mensaje", "Pedido registrado con éxito para el servicio " + servicioId);
        return "pedido_confirmado";
    }
}
