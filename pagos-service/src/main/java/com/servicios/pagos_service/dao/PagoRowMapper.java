package com.servicios.pagos_service.dao;

import org.springframework.jdbc.core.RowMapper;

import com.servicios.pagos_service.model.Pago;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PagoRowMapper implements RowMapper<Pago> {
    @Override
    public Pago mapRow(ResultSet rs, int rowNum) throws SQLException {
        Pago p = new Pago();
        p.setPagoId(rs.getLong("PagoID"));
        p.setPedidoId(rs.getInt("PedidoID"));
        p.setClienteId(rs.getInt("ClienteID"));
        p.setMetodoPago(rs.getString("MetodoPago"));
        p.setMonto(rs.getBigDecimal("Monto"));
        p.setMoneda(rs.getString("Moneda"));
        p.setEstadoPago(rs.getString("EstadoPago"));
        p.setPaypalOrderId(rs.getString("PaypalOrderID"));
        p.setPaypalCaptureId(rs.getString("PaypalCaptureID"));
        p.setTipoTarjeta(rs.getString("TipoTarjeta"));
        p.setUltimosDigitos(rs.getString("UltimosDigitos"));
        p.setFechaPago(rs.getTimestamp("FechaPago").toLocalDateTime());
        p.setDetallesJson(rs.getString("Detalles"));
        return p;
    }
}
