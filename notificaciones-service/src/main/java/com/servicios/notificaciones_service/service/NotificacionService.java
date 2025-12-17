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

    // Inserta una notificación en BD
    public void enviar(Notificacion n) throws SQLException {
        dao.insertar(n);
    }

    // Obtiene historial de notificaciones de un usuario
    public List<Notificacion> historial(Long usuarioId) throws SQLException {
        return dao.obtenerPorUsuario(usuarioId);
    }

    // Obtiene notificaciones globales
    public List<Notificacion> globales() throws SQLException {
        return dao.obtenerGlobales();
    }
}