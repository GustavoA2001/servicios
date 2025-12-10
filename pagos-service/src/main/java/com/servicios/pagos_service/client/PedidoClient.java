package com.servicios.pagos_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.servicios.pagos_service.config.FeignConfigPagos;

@FeignClient(name = "servicios-service", url = "http://localhost:8085/api/pedidos")
public interface PedidoClient {

    @PutMapping("/{id}/activar")
    void activarPedido(@PathVariable("id") Integer id);

    @PutMapping("/{id}/confirmar")
    void confirmarPedido(@PathVariable("id") Integer id);

    @PutMapping("/{id}/denegar")
    void denegarPedido(@PathVariable("id") Integer id);

}