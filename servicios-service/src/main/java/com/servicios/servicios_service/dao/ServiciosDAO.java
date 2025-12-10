package com.servicios.servicios_service.dao;

import com.servicios.servicios_service.model.Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ServiciosDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static class ServicioRowMapper implements RowMapper<Servicio> {
        @Override
        public Servicio mapRow(ResultSet rs, int rowNum) throws SQLException {
            Servicio s = new Servicio();
            s.setId(rs.getInt("ServicioID"));
            s.setTitulo(rs.getString("nomb_servicio"));
            s.setDescripcionCorta(rs.getString("descrip_servicio"));
            s.setPrecioEstimado(rs.getBigDecimal("precio_servicio").doubleValue());
            s.setCategoriaServicioId(rs.getInt("CategoriaServicioID"));
            s.setCategoria(rs.getString("Nomb_Categoria"));
            s.setDescripcionCompleta(rs.getString("Descrip_Categoria"));

            // Construir URL pública hacia imagen-service
            String foto = rs.getString("FotoServicio");
            if (foto != null && !foto.isBlank()) {
                s.setImagen("http://localhost:8090/uploads/servicios/" + foto);
            } else {
                s.setImagen("http://localhost:8090/uploads/servicios/default.jpg");
            }

            return s;
        }
    }

    public List<Servicio> listarServicios() {
        String sql = """
                SELECT s.ServicioID, s.nomb_servicio, s.descrip_servicio, s.precio_servicio,
                       s.CategoriaServicioID, s.FotoServicio,
                       c.Nomb_Categoria, c.Descrip_Categoria
                FROM servicio s
                LEFT JOIN categoriaservicio c ON s.CategoriaServicioID = c.CategoriaServicioID
                """;

        return jdbcTemplate.query(sql, new ServicioRowMapper());
    }

    public Servicio obtenerServicioPorId(int id) {
        String sql = """
                 SELECT s.ServicioID, s.nomb_servicio, s.descrip_servicio, s.precio_servicio,
                       s.CategoriaServicioID, s.FotoServicio,
                       c.Nomb_Categoria, c.Descrip_Categoria
                FROM servicio s
                LEFT JOIN categoriaservicio c ON s.CategoriaServicioID = c.CategoriaServicioID

                            WHERE s.ServicioID = ?
                            """;
        return jdbcTemplate.queryForObject(sql, new ServicioRowMapper(), id);
    }
}