package com.servicios.servicios_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.servicios.servicios_service.model.Pedido;
import com.servicios.servicios_service.service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoRestController {

    @Autowired
    private PedidoService pedidoService;

    // Listar pedidos de un cliente específico
    @GetMapping
    public ResponseEntity<List<Pedido>> listarPedidosPorCliente(@RequestParam Integer clienteId) {
        System.out.println("SERVICIOS-SERVICE: petición recibida para clienteId=" + clienteId);
        List<Pedido> pedidos = pedidoService.listarPedidosPorCliente(clienteId);
        System.out.println("SERVICIOS-SERVICE: retornando " + pedidos.size() + " pedidos");
        return ResponseEntity.ok(pedidos);
    }
    
    

    // Confirmar un pedido
    @PutMapping("/{id}/confirmar")
    public ResponseEntity<Void> confirmarPedido(@PathVariable Integer id) {
        pedidoService.actualizarEstado(id, "CONFIRMADO");
        return ResponseEntity.ok().build();
    }

    // Activar un pedido
    @PutMapping("/{id}/activar")
    public ResponseEntity<Void> activarPedido(@PathVariable Integer id) {
        pedidoService.actualizarEstado(id, "ACTIVO");
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/denegar")
    public ResponseEntity<Void> denegarPedido(@PathVariable Integer id) {
        pedidoService.actualizarEstado(id, "DENEGADO");
        return ResponseEntity.ok().build();
    }
}
