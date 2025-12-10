package com.servicios.servicios_service.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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
import com.servicios.servicios_service.service.DireccionService;
import com.servicios.servicios_service.service.PedidoService;
import com.servicios.servicios_service.service.ServicioService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/pedidos")
public class PedidoController extends BaseController {

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private DireccionService direccionService;

    // Mostrar formulario
    @GetMapping("/nuevo/{servicioId}")
    public String nuevoPedido(@PathVariable("servicioId") Integer servicioId,
            HttpServletRequest request,
            Model model) {
        Servicio servicio = servicioService.obtenerServicioPorId(servicioId);

        Integer clienteId = (Integer) request.getAttribute("clienteId");
        System.out.println("=========");
        System.out.println(clienteId);
        System.out.println("=========");

        model.addAttribute("servicio", servicio);
        model.addAttribute("direcciones", direccionService.obtenerDireccionesCliente(clienteId));
        model.addAttribute("distritos", direccionService.obtenerDistritosHabilitados());

        return "solicitar_servicio";
    }

    @PostMapping("/guardar")
    public String guardarPedido(@RequestParam Integer servicioId,
            @RequestParam String fechaServicio,
            @RequestParam String horaServicio,
            @RequestParam Integer diasServicio,
            @RequestParam Integer horasServicio,
            @RequestParam(required = false) String observaciones,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {

        Integer clienteId = (Integer) request.getAttribute("clienteId");
        Integer direccionId = (Integer) request.getSession().getAttribute("direccionSeleccionadaId");

        Pedido nuevo = new Pedido();
        nuevo.setClienteID(clienteId);
        nuevo.setServicioID(servicioId);
        nuevo.setDireccionID(direccionId);
        nuevo.setFechaServicio(LocalDateTime.of(LocalDate.parse(fechaServicio), LocalTime.parse(horaServicio)));
        nuevo.setDiasServicio(diasServicio);
        nuevo.setHorasServicio(horasServicio);
        nuevo.setObservaciones(observaciones);

        // Registrar pedido en el service
        pedidoService.registrarPedido(nuevo);

        // Mensaje flash opcional
        redirectAttributes.addFlashAttribute("mensaje", "Pedido registrado correctamente");

        // Redirigir a la lista de servicios
        return "redirect:/servicios";
    }

    @GetMapping("/listar")
    public String listarPedidos(HttpServletRequest request, Model model) {
        Integer clienteId = (Integer) request.getAttribute("clienteId");

        List<Pedido> pedidos = pedidoService.listarPedidosPorCliente(clienteId);

        List<Pedido> pendientes = pedidos.stream()
                .filter(p -> "SOLICITADO".equalsIgnoreCase(p.getEstadoPedido()))
                .toList();

        List<Pedido> confirmados = pedidos.stream()
                .filter(p -> "CONFIRMADO".equalsIgnoreCase(p.getEstadoPedido()))
                .toList();

        List<Pedido> activos = pedidos.stream()
                .filter(p -> "ACTIVO".equalsIgnoreCase(p.getEstadoPedido()))
                .toList();

        model.addAttribute("pendientes", pendientes);
        model.addAttribute("confirmados", confirmados);
        model.addAttribute("activos", activos);

        return "pedidos";
    }

}