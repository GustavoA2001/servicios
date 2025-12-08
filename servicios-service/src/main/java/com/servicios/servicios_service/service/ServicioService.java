package com.servicios.servicios_service.service;

import com.servicios.servicios_service.dao.CalificacionDAO;
import com.servicios.servicios_service.dao.EmpleadoDAO;
import com.servicios.servicios_service.dao.ServiciosDAO;
import com.servicios.servicios_service.model.CalificacionServicio;
import com.servicios.servicios_service.model.Empleado;
import com.servicios.servicios_service.model.Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioService {

    @Autowired
    private ServiciosDAO serviciosDAO;

    public List<Servicio> listarServicios() {
        List<Servicio> servicios = serviciosDAO.listarServicios();
        if (servicios == null || servicios.isEmpty()) {
            // Fallback estático
            Servicio demo = new Servicio();
            demo.setId(1);
            demo.setTitulo("Servicio Técnico de Electricidad");
            demo.setDescripcionCorta("Instalaciones y reparaciones eléctricas básicas.");
            demo.setPrecioEstimado(150.00);
            demo.setCategoria("Electricidad");
            return List.of(demo);
        }
        return servicios;
    }

    public Servicio obtenerServicioPorId(int id) {
        try {
            return serviciosDAO.obtenerServicioPorId(id);
        } catch (Exception e) {
            // Fallback si no existe
            Servicio demo = new Servicio();
            demo.setId(id);
            demo.setTitulo("Servicio de Gasfitería");
            demo.setDescripcionCorta("Reparación de tuberías y mantenimiento.");
            demo.setPrecioEstimado(120.00);
            demo.setCategoria("Gasfitería");
            return demo;
        }
    }

    @Autowired
    private CalificacionDAO calificacionDAO;

    public Servicio obtenerServicioConCalificaciones(int id) {
        Servicio servicio = serviciosDAO.obtenerServicioPorId(id);
        servicio.setEtiquetas(List.of("Demo", "Ejemplo")); // opcional
        servicio.setValoracion(calcularPromedio(id));
        return servicio;
    }

    public List<CalificacionServicio> obtenerCalificaciones(int servicioId) {
        return calificacionDAO.listarPorServicio(servicioId);
    }

    private Double calcularPromedio(int servicioId) {
        List<CalificacionServicio> calificaciones = calificacionDAO.listarPorServicio(servicioId);
        if (calificaciones.isEmpty())
            return null;
        return calificaciones.stream()
                .mapToInt(CalificacionServicio::getCalificacion)
                .average()
                .orElse(0.0);
    }

    @Autowired
    private EmpleadoDAO empleadoDAO;

    public Servicio obtenerServicioConTrabajadores(int id) {
        Servicio servicio = serviciosDAO.obtenerServicioPorId(id);
        // trabajadores
        List<Empleado> trabajadores = empleadoDAO.listarTrabajadoresPorServicio(id);
        servicio.setEmpleados(trabajadores);

        // calificaciones
        List<CalificacionServicio> calificaciones = calificacionDAO.listarPorServicio(id);
        servicio.setCalificaciones(calificaciones);

        return servicio;
    }

    public List<Empleado> obtenerTrabajadoresPorServicio(int servicioId) {
        return empleadoDAO.listarTrabajadoresPorServicio(servicioId);
    }
}