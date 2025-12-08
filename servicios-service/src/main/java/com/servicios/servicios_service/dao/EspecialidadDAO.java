package com.servicios.servicios_service.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.servicios.servicios_service.model.Especialidad;

@Repository
public class EspecialidadDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Especialidad> listarPorEmpleadoYServicio(int empleadoId, int servicioId) {
        String sql = """
            SELECT es.EspecialidadID, es.Nomb_Especialidad, es.Descrip_Especialidad, es.Estado_Especialidad
            FROM especialidad es
            INNER JOIN empleadoespecialidad ee ON es.EspecialidadID = ee.EspecialidadID
            WHERE ee.EmpleadoID = ? AND ee.ServicioID = ?
            """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Especialidad e = new Especialidad();
            e.setId(rs.getInt("EspecialidadID"));
            e.setNombre(rs.getString("Nomb_Especialidad"));
            e.setDescripcion(rs.getString("Descrip_Especialidad"));
            e.setEstado(rs.getString("Estado_Especialidad"));
            return e;
        }, empleadoId, servicioId);
    }
}