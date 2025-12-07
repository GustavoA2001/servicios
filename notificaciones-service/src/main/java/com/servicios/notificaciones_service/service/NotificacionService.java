package com.servicios.notificaciones_service.service;

import com.servicios.notificaciones_service.dao.NotificacionDAO;
import com.servicios.notificaciones_service.model.Notificacion;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class NotificacionService {

    private final NotificacionDAO dao;

    public NotificacionService(NotificacionDAO dao) {
        this.dao = dao;
    }

    public void enviar(Notificacion n) throws SQLException {
        dao.insertar(n);
    }

    public List<Notificacion> historial(Long usuarioId) throws SQLException {
        return dao.obtenerPorUsuario(usuarioId);
    }

    public List<Notificacion> globales() throws SQLException {
        return dao.obtenerGlobales();
    }
}