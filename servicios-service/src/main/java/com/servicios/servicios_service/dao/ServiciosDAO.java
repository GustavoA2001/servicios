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
        public Servicio mapRow(@SuppressWarnings("null") ResultSet rs, int rowNum) throws SQLException {
            Servicio s = new Servicio();
            s.setId(rs.getInt("ServicioID"));
            s.setTitulo(rs.getString("Nomb_Servicio"));
            s.setDescripcionCorta(rs.getString("Descrip_Servicio"));
            s.setPrecioEstimado(rs.getBigDecimal("Precio_Servicio").doubleValue());
            s.setCategoriaServicioId(rs.getInt("CategoriaServicioID"));
            s.setCategoria(rs.getString("Nomb_Categoria")); // viene del JOIN
            s.setDescripcionCompleta(rs.getString("Descrip_Categoria")); // opcional
            return s;
        }
    }

    public List<Servicio> listarServicios() {
        String sql = """
            SELECT s.ServicioID, s.Nomb_Servicio, s.Descrip_Servicio, s.Precio_Servicio,
                   s.CategoriaServicioID, c.Nomb_Categoria, c.Descrip_Categoria
            FROM servicio s
            LEFT JOIN categoriaservicio c ON s.CategoriaServicioID = c.CategoriaServicioID
            """;
        return jdbcTemplate.query(sql, new ServicioRowMapper());
    }

    public Servicio obtenerServicioPorId(int id) {
        String sql = """
            SELECT s.ServicioID, s.Nomb_Servicio, s.Descrip_Servicio, s.Precio_Servicio,
                   s.CategoriaServicioID, c.Nomb_Categoria, c.Descrip_Categoria
            FROM servicio s
            LEFT JOIN categoriaservicio c ON s.CategoriaServicioID = c.CategoriaServicioID
            WHERE s.ServicioID = ?
            """;
        return jdbcTemplate.queryForObject(sql, new ServicioRowMapper(), id);
    }
}