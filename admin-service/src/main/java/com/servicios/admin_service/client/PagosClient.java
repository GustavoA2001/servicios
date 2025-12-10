package com.servicios.admin_service.client;

import com.servicios.admin_service.model.InformePagoDTO;
import com.servicios.admin_service.model.Pago;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "pagos-service", url = "http://localhost:8083") // Ajusta el puerto de pagos-service
public interface PagosClient {

    @GetMapping("/api/pagos")
    List<Pago> getAllPagos();

    @GetMapping("/api/pagos/pedido/{pedidoId}")
    List<Pago> getPagosByPedido(@PathVariable("pedidoId") Integer pedidoId);

    @GetMapping("/api/pagos/{pagoId}")
    Pago getPagoById(@PathVariable("pagoId") Long pagoId);

    @GetMapping("/api/pagos/informes")
    List<InformePagoDTO> getInformes();
}