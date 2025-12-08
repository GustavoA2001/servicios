package com.servicios.auditoria_service.dao;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.servicios.auditoria_service.model.Auditoria;

@Repository
public class AuditoriaDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedTemplate;

    public void save(Auditoria auditoria) {
        String sql = "INSERT INTO auditoria (UsuarioTipo, UsuarioID, Servicio, Accion, Entidad, EntidadID, Endpoint, Antes, Despues) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                auditoria.getUsuarioTipo(),
                auditoria.getUsuarioId(),
                auditoria.getServicio(),
                auditoria.getAccion(),
                auditoria.getEntidad(),
                auditoria.getEntidadId(),
                auditoria.getEndpoint(),
                auditoria.getAntes(),
                auditoria.getDespues());
    }

    public List<Auditoria> findAll() {
        String sql = "SELECT * FROM auditoria ORDER BY Fecha DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Auditoria a = new Auditoria();
            a.setAuditoriaId(rs.getLong("AuditoriaID"));
            a.setUsuarioTipo(rs.getString("UsuarioTipo"));
            a.setUsuarioId(rs.getLong("UsuarioID"));
            a.setServicio(rs.getString("Servicio"));
            a.setAccion(rs.getString("Accion"));
            a.setEntidad(rs.getString("Entidad"));
            a.setEntidadId(rs.getLong("EntidadID"));
            a.setEndpoint(rs.getString("Endpoint"));
            a.setFecha(rs.getTimestamp("Fecha"));
            a.setAntes(rs.getString("Antes"));
            a.setDespues(rs.getString("Despues"));
            return a;
        });
    }

    @SuppressWarnings("null")
    public List<Auditoria> findFiltered(String orderBy, String direction,
            LocalDate fecha, String usuarioTipo,
            Long usuarioId, String servicio, String accion) {

        StringBuilder sql = new StringBuilder("SELECT * FROM auditoria WHERE 1=1 ");
        Map<String, Object> params = new HashMap<>();

        if (fecha != null) {
            sql.append(" AND DATE(Fecha) = :fecha");
            params.put("fecha", fecha);
        }
        if (usuarioTipo != null && !usuarioTipo.isEmpty()) {
            sql.append(" AND UsuarioTipo = :usuarioTipo");
            params.put("usuarioTipo", usuarioTipo);
        }
        if (usuarioId != null) {
            sql.append(" AND UsuarioID = :usuarioId");
            params.put("usuarioId", usuarioId);
        }
        if (servicio != null && !servicio.isEmpty()) {
            sql.append(" AND Servicio = :servicio");
            params.put("servicio", servicio);
        }
        if (accion != null && !accion.isEmpty()) {
            sql.append(" AND Accion LIKE :accion");
            params.put("accion", accion + "%");
        }

        if (orderBy == null || orderBy.isEmpty()) {
            orderBy = "Fecha";
        }
        if (!"ASC".equalsIgnoreCase(direction)) {
            direction = "DESC";
        }
        sql.append(" ORDER BY ").append(orderBy).append(" ").append(direction);

        return namedTemplate.query(sql.toString(), params, (rs, rowNum) -> {
            Auditoria a = new Auditoria();
            a.setAuditoriaId(rs.getLong("AuditoriaID"));
            a.setUsuarioTipo(rs.getString("UsuarioTipo"));
            a.setUsuarioId(rs.getLong("UsuarioID"));
            a.setServicio(rs.getString("Servicio"));
            a.setAccion(rs.getString("Accion"));
            a.setEntidad(rs.getString("Entidad"));
            a.setEntidadId(rs.getLong("EntidadID"));
            a.setEndpoint(rs.getString("Endpoint"));
            a.setFecha(rs.getTimestamp("Fecha"));
            a.setAntes(rs.getString("Antes"));
            a.setDespues(rs.getString("Despues"));
            return a;
        });
    }

    @SuppressWarnings("deprecation")
    public List<Auditoria> findByFecha(LocalDate fecha) {
        String sql = "SELECT * FROM auditoria WHERE DATE(Fecha) = ? ORDER BY Fecha DESC";
        return jdbcTemplate.query(sql, new Object[]{fecha}, (rs, rowNum) -> {
            Auditoria a = new Auditoria();
            a.setAuditoriaId(rs.getLong("AuditoriaID"));
            a.setUsuarioTipo(rs.getString("UsuarioTipo"));
            a.setUsuarioId(rs.getLong("UsuarioID"));
            a.setServicio(rs.getString("Servicio"));
            a.setAccion(rs.getString("Accion"));
            a.setEntidad(rs.getString("Entidad"));
            a.setEntidadId(rs.getLong("EntidadID"));
            a.setEndpoint(rs.getString("Endpoint"));
            a.setFecha(rs.getTimestamp("Fecha"));
            a.setAntes(rs.getString("Antes"));
            a.setDespues(rs.getString("Despues"));
            return a;
        });
    }
}
