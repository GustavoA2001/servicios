package com.servicios.admin_service.service;

import com.servicios.admin_service.dao.ServicioDAO;
import com.servicios.admin_service.model.Servicio;
import com.servicios.admin_service.model.Pedido;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioService {

    private final ServicioDAO servicioDAO;

    public ServicioService(ServicioDAO servicioDAO) {
        this.servicioDAO = servicioDAO;
    }

    /**
     * Obtiene todos los servicios registrados
     */
    public List<Servicio> obtenerServicios() {
        return servicioDAO.obtenerServicios();
    }

    /**
     * Busca servicios por término (nombre, descripción, categoría, empleado, etc.)
     */
    public List<Servicio> buscarServicios(String search) {
        if (search == null || search.trim().isEmpty()) {
            return servicioDAO.obtenerServicios();
        }
        return servicioDAO.buscarServicios(search.trim());
    }

    /**
     * Obtiene todos los pedidos (clientes) que han solicitado un servicio específico
     */
    public List<Pedido> obtenerClientesServicio(int servicioId) {
        return servicioDAO.obtenerClientesServicio(servicioId);
    }

    public void actualizarEstadoServicio(int servicioId, String nuevoEstado) {
        servicioDAO.actualizarEstadoServicio(servicioId, nuevoEstado);
    }
}
