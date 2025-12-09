package com.servicios.pagos_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * PagoController para pagos-service (dominio: servicios: electricista, plomería, etc.)
 * Patrón: GET -> mostrar formulario; POST -> procesar (mock) y mostrar confirmación.
 */
@Controller
@RequestMapping("/pago")
public class PagoController {

    @GetMapping
    public String mostrarFormulario(@RequestParam(value = "servicioId", required = false) Long servicioId,
                                    Model model) {

        if (servicioId == null) servicioId = 1L;

        model.addAttribute("servicioId", servicioId);
        model.addAttribute("servicioNombre", "Electricista - Reparación de tablero");
        model.addAttribute("proveedorNombre", "Soluciones Eléctricas SAC");
        model.addAttribute("direccion", "Calle Falsa 123, San Isidro");
        model.addAttribute("fechaServicio", "2025-12-20");
        model.addAttribute("duracion", "2 horas");
        model.addAttribute("precioPresentacion", "S/ 180");
        model.addAttribute("precioRaw", 180.0);
        model.addAttribute("contactoProveedor", "999-111-222");

        return "pago";
    }

    @PostMapping("/submit")
    public String procesarPago(
            @RequestParam Long servicioId,
            @RequestParam String titular,
            @RequestParam String telefono,
            @RequestParam String email,
            @RequestParam(required = false) String metodo,
            @RequestParam(required = false) String notas,
            @RequestParam(required = false) String numero,
            @RequestParam(required = false) String fecha,
            @RequestParam(required = false) String cvv,
            Model model
    ) {

        model.addAttribute("servicioId", servicioId);
        model.addAttribute("servicioNombre", "Electricista - Reparación de tablero");
        model.addAttribute("proveedorNombre", "Soluciones Eléctricas SAC");
        model.addAttribute("nombreCliente", titular);
        model.addAttribute("telefonoCliente", telefono);
        model.addAttribute("emailCliente", email);
        model.addAttribute("metodoPago", metodo == null ? "Tarjeta" : metodo);
        model.addAttribute("notas", (notas == null || notas.isBlank()) ? "Sin observaciones" : notas);
        model.addAttribute("total", "S/ 180");
        model.addAttribute("txId", "MOCK-" + System.currentTimeMillis());

        return "confirmacion_pago";
    }
}
