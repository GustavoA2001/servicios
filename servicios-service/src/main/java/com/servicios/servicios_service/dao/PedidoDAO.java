package com.servicios.servicios_service.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.servicios.servicios_service.model.Pedido;

@Repository
public class PedidoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertarPedido(Pedido pedido) {
        String sql = "INSERT INTO pedido (" +
                "ClienteID, FechaPedido, EstadoPedido, ServicioID, DuracionPedido, " +
                "CostoTotal, DireccionID, FechaServicio, HorasServicio, DiasServicio, Observaciones, Bloques" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                pedido.getClienteID(),
                pedido.getFechaPedido(),
                pedido.getEstadoPedido(),
                pedido.getServicioID(),
                pedido.getDuracionPedido(),
                pedido.getCostoTotal(),
                pedido.getDireccionID(),
                pedido.getFechaServicio(),
                pedido.getHorasServicio(),
                pedido.getDiasServicio(),
                pedido.getObservaciones(),
                pedido.getBloques());
    }

    @SuppressWarnings("deprecation")
    public List<Pedido> listarPedidosPorCliente(int clienteId) {
        String sql = "SELECT * FROM pedido WHERE ClienteID = ?";
        return jdbcTemplate.query(sql, new Object[]{clienteId}, (rs, rowNum) -> {
            Pedido p = new Pedido();
            p.setPedidoID(rs.getInt("PedidoID"));
            p.setClienteID(rs.getInt("ClienteID"));
            p.setServicioID(rs.getInt("ServicioID"));
            p.setDireccionID(rs.getInt("DireccionID"));
    
            // Manejo seguro de fechas
            java.sql.Timestamp tsPedido = rs.getTimestamp("FechaPedido");
            p.setFechaPedido(tsPedido != null ? tsPedido.toLocalDateTime() : null);
    
            p.setEstadoPedido(rs.getString("EstadoPedido"));
    
            java.sql.Timestamp tsServicio = rs.getTimestamp("FechaServicio");
            p.setFechaServicio(tsServicio != null ? tsServicio.toLocalDateTime() : null);
    
            p.setDiasServicio(rs.getInt("DiasServicio"));
            p.setHorasServicio(rs.getInt("HorasServicio"));
            p.setDuracionPedido(rs.getInt("DuracionPedido"));
            p.setCostoTotal(rs.getBigDecimal("CostoTotal"));
            p.setObservaciones(rs.getString("Observaciones"));
            p.setBloques(rs.getString("Bloques"));
            return p;
        });
    }
    
    
    public void actualizarEstado(Integer pedidoId, String nuevoEstado) {
        String sql = "UPDATE pedido SET EstadoPedido = ? WHERE PedidoID = ?";
        jdbcTemplate.update(sql, nuevoEstado, pedidoId);
    }
}
