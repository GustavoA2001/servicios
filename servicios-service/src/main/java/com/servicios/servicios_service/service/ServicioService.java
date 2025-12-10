package com.servicios.servicios_service.service;

import com.servicios.servicios_service.dao.CalificacionDAO;
import com.servicios.servicios_service.dao.EmpleadoDAO;
import com.servicios.servicios_service.dao.ServiciosDAO;
import com.servicios.servicios_service.model.CalificacionServicio;
import com.servicios.servicios_service.model.Empleado;
import com.servicios.servicios_service.model.Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ServicioService {

    @Autowired
    private ServiciosDAO serviciosDAO;

    @Autowired
    private CalificacionDAO calificacionDAO;

    @Autowired
    private EmpleadoDAO empleadoDAO;

    /** LISTAR SERVICIOS */
    public List<Servicio> listarServicios() {
        List<Servicio> servicios = serviciosDAO.listarServicios();

        if (servicios == null || servicios.isEmpty()) {
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

    /** OBTENER SERVICIO POR ID (BÁSICO) */
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

    /** OBTENER SERVICIO COMPLETO (CON TRABAJADORES Y CALIFICACIONES) */
    public Servicio obtenerServicioCompleto(int id) {
        Servicio servicio = serviciosDAO.obtenerServicioPorId(id);

        // trabajadores
        List<Empleado> trabajadores = empleadoDAO.listarTrabajadoresPorServicio(id);
        servicio.setEmpleados(trabajadores);

        // calificaciones
        List<CalificacionServicio> calificaciones = calificacionDAO.listarPorServicio(id);
        servicio.setCalificaciones(calificaciones);

        // promedio calificación
        double promedio = calificaciones.stream()
                .mapToInt(CalificacionServicio::getCalificacion)
                .average()
                .orElse(0.0);
        servicio.setValoracion(promedio);

        // etiquetas opcionales
        servicio.setEtiquetas(List.of("Demo", "Ejemplo"));

        // datos extra opcionales
        servicio.setDuracionHoras(2.0);
        servicio.setTelefonoContacto("999-888-777");
        servicio.setDisponible(true);

        return servicio;
    }

    public BigDecimal obtenerPrecio(int id) {
        Servicio servicio = serviciosDAO.obtenerServicioPorId(id);
        if (servicio != null && servicio.getPrecioEstimado() != null) {
            return BigDecimal.valueOf(servicio.getPrecioEstimado());
        }
        // fallback si no existe
        return BigDecimal.ZERO;
    }
}
