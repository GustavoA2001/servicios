package com.servicios.servicios_service.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.servicios.servicios_service.model.DireccionCliente;

@Repository
public class DireccionDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<DireccionCliente> listarPorCliente(int clienteId) {
        String sql = "SELECT dc.DireccionID, dc.Direccion, dc.Referencia, d.Nom_Distrito " +
                     "FROM direccioncliente dc " +
                     "LEFT JOIN distrito d ON dc.DistritoID = d.DistritoID " +
                     "WHERE dc.ClienteID = ?";
        return jdbcTemplate.query(sql, new Object[]{clienteId}, (rs, rowNum) -> {
            DireccionCliente dir = new DireccionCliente();
            dir.setIdDireccion(rs.getInt("DireccionID"));
            dir.setDireccion(rs.getString("Direccion"));
            dir.setReferencia(rs.getString("Referencia"));
            dir.setDistrito(rs.getString("Nom_Distrito"));
            return dir;
        });
    }

    public void guardarDireccion(DireccionCliente direccion) {
        String sql = "INSERT INTO direccioncliente (Direccion, Referencia, EstadoDireccion, ClienteID, DistritoID) " +
                     "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                direccion.getDireccion(),
                direccion.getReferencia(),
                "ACTIVO",
                direccion.getClienteId(),
                direccion.getDistritoId());
    }
}
