package com.servicios.servicios_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.servicios.servicios_service.model.Pedido;
import com.servicios.servicios_service.model.Servicio;
import com.servicios.servicios_service.service.PedidoService;
import com.servicios.servicios_service.service.ServicioService;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private PedidoService pedidoService;

    // Mostrar formulario
    @GetMapping("/nuevo/{servicioId}")
    public String nuevoPedido(@PathVariable("servicioId") Integer servicioId, Model model) {
        Servicio servicio = servicioService.obtenerServicioPorId(servicioId);

        model.addAttribute("servicioId", servicio.getId());
        model.addAttribute("tituloServicio", servicio.getTitulo());
        model.addAttribute("precioEstimado", servicio.getPrecioEstimado());

        return "form_pedido"; // tu formulario Thymeleaf
    }

    // Guardar pedido
    @PostMapping("/guardar")
    public String guardarPedido(@RequestParam("servicioId") Integer servicioId,
                                @RequestParam("direccion") String direccion,
                                @RequestParam("fecha") String fecha,
                                @RequestParam("dias") String dias,
                                @RequestParam("observaciones") String observaciones,
                                RedirectAttributes redirectAttributes) {
        Pedido pedido = new Pedido();
        pedido.setServicioId(servicioId);
        pedido.setDireccion(direccion);
        pedido.setFecha(fecha);
        pedido.setDias(dias);
        pedido.setObservaciones(observaciones);

        pedidoService.guardarPedido(pedido);

        redirectAttributes.addFlashAttribute("mensaje", "Pedido registrado correctamente");
        return "redirect:/servicios/" + servicioId;
    }
}