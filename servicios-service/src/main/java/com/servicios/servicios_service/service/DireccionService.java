package com.servicios.servicios_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servicios.servicios_service.dao.DireccionDAO;
import com.servicios.servicios_service.dao.DistritoDAO;
import com.servicios.servicios_service.model.DireccionCliente;
import com.servicios.servicios_service.model.Distrito;

@Service
public class DireccionService {

    @Autowired
    private DireccionDAO direccionDAO;

    @Autowired
    private DistritoDAO distritoDAO;

    public List<DireccionCliente> obtenerDireccionesCliente(int clienteId) {
        return direccionDAO.listarDireccionesPorCliente(clienteId);
    }

    public List<Distrito> obtenerDistritosHabilitados() {
        return distritoDAO.listarDistritosHabilitados();
    }

    public DireccionCliente registrarDireccion(DireccionCliente direccion) {
        return direccionDAO.insertarDireccion(direccion);
    }
}
