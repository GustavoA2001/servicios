package com.servicios.empleado_service.repository;

import com.servicios.empleado_service.model.CategoriaServicio;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class CategoriaServicioRepository {

    private final JdbcTemplate jdbc;

    public CategoriaServicioRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<CategoriaServicio> listar() {
        String sql = "SELECT CategoriaServicioID, Nomb_Categoria, Descrip_Categoria FROM categoriaservicio";
    
        return jdbc.query(sql, (rs, rowNum) -> {
            CategoriaServicio c = new CategoriaServicio();
            c.setCategoriaServicioID(rs.getInt("CategoriaServicioID"));
            c.setNombre(rs.getString("Nomb_Categoria"));
            c.setDescripcion(rs.getString("Descrip_Categoria"));
            // No existe campo Estado, así que no se asigna
            return c;
        });
    }
    
}
