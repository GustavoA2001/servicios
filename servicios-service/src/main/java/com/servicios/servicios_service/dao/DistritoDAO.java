package com.servicios.servicios_service.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.servicios.servicios_service.model.Distrito;

@Repository
public class DistritoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Distrito> listarDistritosHabilitados() {
        String sql = """
            SELECT DistritoID, Nom_Distrito, Estado_Distrito
            FROM distrito
            WHERE Estado_Distrito = 'Habilitado'
            ORDER BY Nom_Distrito
        """;
    
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Distrito d = new Distrito();
            d.setDistritoID(rs.getInt("DistritoID"));
            d.setNomb_Distrito(rs.getString("Nom_Distrito"));
            d.setEstado_Distrito(rs.getString("Estado_Distrito"));
            return d;
        });
    }
    
}