package com.servicios.pagos_service.controller;

import com.servicios.pagos_service.model.InformePagoDTO;
import com.servicios.pagos_service.model.Pago;
import com.servicios.pagos_service.service.PagoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoRestController {

    private final PagoService pagoService;

    public PagoRestController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    // Obtener todos los pagos
    @GetMapping
    public ResponseEntity<List<Pago>> getAllPagos() {
        return ResponseEntity.ok(pagoService.findAll());
    }

    // Obtener pagos por pedidoId
    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<Pago>> getPagosByPedido(@PathVariable Integer pedidoId) {
        return ResponseEntity.ok(pagoService.findByPedidoId(pedidoId));
    }

    // Obtener un pago específico por su id
    @GetMapping("/{pagoId}")
    public ResponseEntity<Pago> getPagoById(@PathVariable Long pagoId) {
        return pagoService.findById(pagoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/informes")
    public ResponseEntity<List<InformePagoDTO>> getInformes() {
        return ResponseEntity.ok(pagoService.findAllInformes());
    }

}