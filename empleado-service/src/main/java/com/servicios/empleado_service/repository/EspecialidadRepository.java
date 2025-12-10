package com.servicios.empleado_service.repository;

import com.servicios.empleado_service.model.Especialidad;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class EspecialidadRepository {

    private final JdbcTemplate jdbc;

    public EspecialidadRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Especialidad> listar() {
        String sql = "SELECT EspecialidadID, Nomb_Especialidad, Descrip_Especialidad, Estado_Especialidad " +
                     "FROM especialidad WHERE Estado_Especialidad = 'Habilitado'";
    
        return jdbc.query(sql, (rs, rowNum) -> {
            Especialidad e = new Especialidad();
            e.setEspecialidadID(rs.getInt("EspecialidadID"));
            e.setNombre(rs.getString("Nomb_Especialidad"));
            e.setDescripcion(rs.getString("Descrip_Especialidad"));
            e.setEstado(rs.getString("Estado_Especialidad"));
            return e;
        });
    }
    
}
