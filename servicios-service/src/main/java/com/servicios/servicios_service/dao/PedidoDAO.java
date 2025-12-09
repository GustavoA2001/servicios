package com.servicios.servicios_service.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.servicios.servicios_service.model.Pedido;

@Repository
public class PedidoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertarPedido(Pedido pedido) {
        String sql = "INSERT INTO pedido (ServicioID, Direccion, Fecha, Dias, Observaciones) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                pedido.getServicioId(),
                pedido.getDireccion(),
                pedido.getFecha(),
                pedido.getDias(),
                pedido.getObservaciones());
    }
}