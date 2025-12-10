package com.servicios.pagos_service.service;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.servicios.pagos_service.client.PedidoClient;
import com.servicios.pagos_service.dao.PagoDAO;

@Service
public class PagoService {

    private final PaypalService paypalService;
    private final PagoDAO pagoDAO;
    private final PedidoClient pedidoClient;
    private final ObjectMapper mapper = new ObjectMapper(); // Usamos solo Jackson

    public PagoService(PaypalService paypalService, PagoDAO pagoDAO, PedidoClient pedidoClient) {
        this.paypalService = paypalService;
        this.pagoDAO = pagoDAO;
        this.pedidoClient = pedidoClient;
    }

    // =================== CREAR ORDEN ===================
    public Map<String, String> crearOrden(Integer pedidoId, Integer clienteId, BigDecimal costo) {

        System.out.println("[SERVICE] Crear orden PayPal...");

        Map<String, String> orden = paypalService.crearOrden(pedidoId, costo);

        System.out.println("[SERVICE] Orden PayPal: " + orden);

        pagoDAO.insertarPago(
                pedidoId,
                clienteId,
                "PAYPAL",
                costo,
                "USD",
                "CREADO",
                orden.get("id")
        );

        pagoDAO.insertarTransaccion(
                orden.get("id"),
                "CREACION",
                "CREADO",
                orden.toString()
        );

        return orden;
    }

    // =================== CAPTURAR ORDEN ===================
    public boolean capturarOrden(String orderId, Integer pedidoId) {

        System.out.println("[SERVICE] Capturando orden PayPal ID=" + orderId);

        Map<String, Object> capture = paypalService.capturarOrden(orderId);

        System.out.println("[SERVICE] Respuesta captura: " + capture);

        String status = (String) capture.get("status");

        try {
            // Convertimos TODA la respuesta PayPal a JSON con Jackson
            String detallesJson = mapper.writeValueAsString(capture);

            if ("COMPLETED".equalsIgnoreCase(status)) {

                pagoDAO.actualizarPago(
                        orderId,
                        "COMPLETADO",
                        (String) capture.get("captureId"),
                        detallesJson
                );

                String transaccionJson = mapper.writeValueAsString(
                        Map.of("paypal_status", capture.get("status"))
                );

                pagoDAO.insertarTransaccion(
                        orderId,
                        "CAPTURA",
                        "COMPLETADO",
                        transaccionJson
                );

                // ACTIVAR PEDIDO EN BACKEND
                System.out.println("[SERVICE] Activando pedido ID=" + pedidoId);
                pedidoClient.activarPedido(pedidoId);

                return true;

            } else {

                pagoDAO.actualizarPago(orderId, "FALLIDO", null, detallesJson);

                String transaccionJson = mapper.writeValueAsString(
                        Map.of("paypal_status", capture.get("status"))
                );

                pagoDAO.insertarTransaccion(
                        orderId,
                        "CAPTURA",
                        "FALLIDO",
                        transaccionJson
                );

                return false;
            }

        } catch (Exception e) {
            throw new RuntimeException("Error serializando detalles captura", e);
        }
    }
}
