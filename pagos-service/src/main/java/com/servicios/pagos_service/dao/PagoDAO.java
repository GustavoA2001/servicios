package com.servicios.pagos_service.dao;

import java.math.BigDecimal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class PagoDAO {

    private final JdbcTemplate jdbcTemplate;

    public PagoDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertarPago(Integer pedidoId, Integer clienteId, String metodo,
            BigDecimal monto, String moneda, String estado, String paypalOrderId) {

        System.out.println("[DAO] Insertando pago pedidoId=" + pedidoId);

        jdbcTemplate.update("""
                INSERT INTO pago (PedidoID, ClienteID, MetodoPago, Monto, Moneda, EstadoPago, PaypalOrderID)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """, pedidoId, clienteId, metodo, monto, moneda, estado, paypalOrderId);
    }

    public void actualizarPago(String paypalOrderId, String estado, String captureId, String detalles) {

        System.out.println("[DAO] Actualizando pago PayPalOrderID=" + paypalOrderId);

        jdbcTemplate.update("""
                    UPDATE pago SET EstadoPago=?, PaypalCaptureID=?, FechaPago=NOW(), Detalles=?
                    WHERE PaypalOrderID=?
                """, estado, captureId, detalles, paypalOrderId);
    }

    public void insertarTransaccion(String paypalOrderId, String tipo, String estado, String detallesJson) {
        System.out.println("[DAO] Insertando transacción PayPalOrderID=" + paypalOrderId);
    
        String sql =
            "INSERT INTO transaccion (PagoID, Tipo, Estado, Detalles) " +
            "VALUES ((SELECT PagoID FROM pago WHERE PaypalOrderID=?), ?, ?, ?)";
    
        jdbcTemplate.update(sql, paypalOrderId, tipo, estado, detallesJson);
    }
    
    
}
