package com.servicios.notificaciones_service.dao;

import com.servicios.notificaciones_service.model.Notificacion;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class NotificacionDAO {

    private final JdbcTemplate jdbc;

    public NotificacionDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void insertar(Notificacion n) {
        String sql = "INSERT INTO notificacion (UsuarioID, PedidoID, TipoUsuario, TipoNotificacion, Canal, Mensaje, Estado, FechaCreacion) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        jdbc.update(sql,
                n.getUsuarioId(),
                n.getPedidoId(),
                n.getTipoUsuario(),
                n.getTipoNotificacion(),
                n.getCanal(),
                n.getMensaje(),
                n.getEstado(),
                n.getFechaCreacion()
        );
    }

    public List<Notificacion> obtenerPorUsuario(Long usuarioId) {
        String sql = "SELECT * FROM notificacion WHERE UsuarioID = ?";

        return jdbc.query(sql, new Object[]{usuarioId}, notificacionMapper);
    }

    public List<Notificacion> obtenerGlobales() {
        String sql = "SELECT * FROM notificacion WHERE TipoNotificacion = 'GLOBAL'";

        return jdbc.query(sql, notificacionMapper);
    }

    private final RowMapper<Notificacion> notificacionMapper = new RowMapper<Notificacion>() {
        @Override
        public Notificacion mapRow(ResultSet rs, int rowNum) throws SQLException {
            Notificacion n = new Notificacion();
            n.setNotificacionId(rs.getLong("NotificacionID"));
            n.setUsuarioId(rs.getLong("UsuarioID"));
            n.setPedidoId(rs.getLong("PedidoID"));
            n.setTipoUsuario(rs.getString("TipoUsuario"));
            n.setTipoNotificacion(rs.getString("TipoNotificacion"));
            n.setCanal(rs.getString("Canal"));
            n.setMensaje(rs.getString("Mensaje"));
            n.setEstado(rs.getString("Estado"));
            n.setFechaCreacion(rs.getTimestamp("FechaCreacion").toLocalDateTime());
            return n;
        }
    };
}
