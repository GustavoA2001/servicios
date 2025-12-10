package com.servicios.pagos_service.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.servicios.pagos_service.client.PedidoClient;
import com.servicios.pagos_service.service.PagoService;
import com.servicios.pagos_service.service.PaypalService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/pagos")
public class PagoController {

    @Value("${paypal.client.id}")
    private String paypalClientId;

    private final PaypalService paypalService;
    private final PagoService pagoService;
    private final PedidoClient pedidoClient;

    public PagoController(PaypalService paypalService, PedidoClient pedidoClient, PagoService pagoService) {
        this.paypalService = paypalService;
        this.pedidoClient = pedidoClient;
        this.pagoService = pagoService;
    }

    // =================== CHECKOUT ===================
    @GetMapping("/checkout")
    public String checkout(@RequestParam Integer pedidoId,
                           @RequestParam BigDecimal costo,
                           HttpServletRequest request,
                           Model model) {
        Integer clienteId = (Integer) request.getAttribute("clienteId");
    
        System.out.println(">>> Entrando a /checkout");
        System.out.println(">>> pedidoId=" + pedidoId);
        System.out.println(">>> costo=" + costo);
        System.out.println(">>> clienteId=" + clienteId);
        System.out.println(">>> paypalClientId=" + paypalClientId);
    
        model.addAttribute("pedidoId", pedidoId);
        model.addAttribute("costo", costo);
        model.addAttribute("clienteId", clienteId);
    
        return "checkout";
    }
    
    // =================== CREAR ORDEN PAYPAL ===================
    @PostMapping("/paypal/create")
    public ResponseEntity<Map<String, String>> crearOrden(@RequestParam Integer pedidoId,
                                                          @RequestParam BigDecimal costo,
                                                          HttpServletRequest request) {
        Integer clienteId = (Integer) request.getAttribute("clienteId");

        System.out.println("\n>>> [CONTROLLER] /paypal/create pedidoId=" + pedidoId + ", costo=" + costo);

        Map<String, String> orden = pagoService.crearOrden(pedidoId, clienteId, costo);

        System.out.println(">>> Orden creada OK: " + orden + "\n");

        return ResponseEntity.ok(orden);
    }
    
    // =================== CAPTURAR ORDEN ===================
    /*
    @PostMapping("/paypal/capture")
    public ResponseEntity<?> capturar(@RequestParam String orderId,
                                      @RequestParam Integer pedidoId) {
        boolean ok = pagoService.capturarOrden(orderId, pedidoId);
        return ok ? ResponseEntity.ok(Map.of("status", "COMPLETADO"))
                  : ResponseEntity.status(400).body(Map.of("status", "FALLIDO"));
    }
    */
    @PostMapping("/paypal/capture")
    public ResponseEntity<?> capturar(@RequestParam String orderId,
                                      @RequestParam Integer pedidoId) {

        System.out.println("\n>>> [CONTROLLER] /paypal/capture orderId=" + orderId);

        boolean ok = pagoService.capturarOrden(orderId, pedidoId);

        if (ok) {
            System.out.println(">>> Captura exitosa. Pedido ACTIVADO.\n");
            return ResponseEntity.ok(Map.of("status", "COMPLETADO"));
        } else {
            System.out.println(">>> Captura fallida.\n");
            return ResponseEntity.status(400).body(Map.of("status", "FALLIDO"));
        }
    }

    @GetMapping("/exito")
    public String exito(@RequestParam(required = false) Integer pedidoId, Model model) {
        System.out.println(">>> MOSTRANDO VISTA: exito.html | pedidoId=" + pedidoId);
        model.addAttribute("pedidoId", pedidoId);
        return "exito";
    }
    
    @GetMapping("/cancelado")
    public String cancelado() {
        System.out.println(">>> MOSTRANDO VISTA: cancelado.html");
        return "cancelado";
    }
    

    
}
