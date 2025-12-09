package com.servicios.servicios_service.dao;

import com.servicios.servicios_service.model.CalificacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CalificacionDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static class CalificacionRowMapper implements RowMapper<CalificacionServicio> {
        @Override
        public CalificacionServicio mapRow(@SuppressWarnings("null") ResultSet rs, int rowNum) throws SQLException {
            CalificacionServicio c = new CalificacionServicio();
            c.setId(rs.getInt("CalificacionID"));
            c.setPedidoId(rs.getInt("PedidoID"));
            c.setCalificacion(rs.getInt("Calificacion"));
            c.setComentario(rs.getString("Comentario"));
            c.setFechaCalificacion(rs.getTimestamp("FechaCalificacion").toLocalDateTime());
            
            return c;
        }
    }

    public List<CalificacionServicio> listarPorServicio(int servicioId) {
        String sql = """
            SELECT cs.* 
            FROM calificacionservicio cs
            INNER JOIN pedido p ON cs.PedidoID = p.PedidoID
            WHERE p.ServicioID = ?
            """;
        return jdbcTemplate.query(sql, new CalificacionRowMapper(), servicioId);
    }
}