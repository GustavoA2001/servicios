package com.servicios.servicios_service.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.servicios.servicios_service.model.DireccionCliente;
import com.servicios.servicios_service.model.Distrito;

@Repository
public class DireccionDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static class DireccionRowMapper implements RowMapper<DireccionCliente> {
        @Override
        public DireccionCliente mapRow(ResultSet rs, int rowNum) throws SQLException {

            DireccionCliente dir = new DireccionCliente();
            dir.setDireccionID(rs.getInt("DireccionID"));
            dir.setDireccion(rs.getString("Direccion"));
            dir.setReferencia(rs.getString("Referencia"));
            dir.setEstadoDireccion(rs.getString("EstadoDireccion"));

            // Crear distrito asociado
            Distrito distrito = new Distrito();
            distrito.setDistritoID(rs.getInt("DistritoID")); // nombre correcto
            distrito.setNomb_Distrito(rs.getString("Nom_Distrito"));
            distrito.setEstado_Distrito(rs.getString("Estado_Distrito"));

            dir.setDistrito(distrito);
            return dir;
        }
    }

    public List<DireccionCliente> listarDireccionesPorCliente(int clienteId) {
        String sql = """
                    SELECT
                        dc.DireccionID,
                        dc.Direccion,
                        dc.Referencia,
                        dc.EstadoDireccion,
                        d.DistritoID,
                        d.Nom_Distrito,
                        d.Estado_Distrito
                    FROM direccioncliente dc
                    INNER JOIN distrito d ON dc.DistritoID = d.DistritoID
                    WHERE dc.ClienteID = ?
                      AND d.Estado_Distrito = 'Habilitado'
                    ORDER BY dc.DireccionID DESC
                """;

        return jdbcTemplate.query(sql, new DireccionRowMapper(), clienteId);
    }

    public DireccionCliente insertarDireccion(DireccionCliente dir) {
        String sql = """
            INSERT INTO direccioncliente (Direccion, Referencia, EstadoDireccion, ClienteID, DistritoID)
            VALUES (?, ?, ?, ?, ?)
        """;
    
        jdbcTemplate.update(sql,
            dir.getDireccion(),
            dir.getReferencia(),
            dir.getEstadoDireccion(),
            dir.getClienteID(),
            dir.getDistritoID()
        );
    
        // Recuperar el último ID generado
        Integer id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        dir.setDireccionID(id);
        return dir;
    }
}
