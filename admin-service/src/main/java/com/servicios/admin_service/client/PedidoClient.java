package com.servicios.admin_service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.servicios.admin_service.model.Pedido;

@FeignClient(
    name = "servicios-service",
    url = "http://localhost:8085/api/pedidos",
    configuration = com.servicios.admin_service.config.FeignConfig.class
)
public interface PedidoClient {

    @GetMapping
    List<Pedido> listarPedidosPorCliente(@RequestParam("clienteId") Integer clienteId);

    @PutMapping("/{id}/confirmar")
    void confirmarPedido(@PathVariable("id") Integer id);

    @PutMapping("/{id}/activar")
    void activarPedido(@PathVariable("id") Integer id);

    @PutMapping("/{id}/denegar")
    void denegarPedido(@PathVariable("id") Integer id);
}
