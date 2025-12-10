package com.servicios.pagos_service.dao;

import org.springframework.jdbc.core.RowMapper;

import com.servicios.pagos_service.model.InformePagoDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InformePagoRowMapper implements RowMapper<InformePagoDTO> {
    @Override
    public InformePagoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        InformePagoDTO dto = new InformePagoDTO();
        dto.setPagoId(rs.getLong("PagoID"));
        dto.setPedidoId(rs.getInt("PedidoID"));
        dto.setClienteId(rs.getInt("ClienteID"));
        dto.setEmpleadoId(rs.getLong("EmpleadoID"));
        dto.setMetodoPago(rs.getString("MetodoPago"));
        dto.setMonto(rs.getBigDecimal("Monto"));
        dto.setMoneda(rs.getString("Moneda"));
        dto.setEstadoPago(rs.getString("EstadoPago"));
        dto.setPaypalOrderId(rs.getString("PaypalOrderID"));
        dto.setPaypalCaptureId(rs.getString("PaypalCaptureID"));
        dto.setTipoTarjeta(rs.getString("TipoTarjeta"));
        dto.setUltimosDigitos(rs.getString("UltimosDigitos"));
        dto.setFechaPago(rs.getTimestamp("FechaPago").toLocalDateTime());
        dto.setDetallesJson(rs.getString("Detalles"));

        // Campos enriquecidos
        dto.setNombreCliente(rs.getString("NombreCliente"));
        dto.setServicioContratado(rs.getString("ServicioContratado"));
        dto.setNombreEmpleado(rs.getString("NombreEmpleado"));

        return dto;
    }
}
