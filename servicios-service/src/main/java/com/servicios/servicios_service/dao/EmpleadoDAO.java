package com.servicios.servicios_service.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import com.servicios.servicios_service.client.ImagenesClient;
import com.servicios.servicios_service.model.Empleado;

@Repository
public class EmpleadoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ImagenesClient imagenesClient;

    @Autowired
    private EspecialidadDAO especialidadDAO;

    public List<Empleado> listarTrabajadoresPorServicio(int servicioId) {
        String sql = """
            SELECT e.EmpleadoID, e.Nomb_Empleado, e.Apel_Empleado, e.Email_Empleado,
                   e.telefono, e.EstadoEmpleado, e.FotoEmpleado, e.RolID
            FROM empleado e
            INNER JOIN empleadoespecialidad ee ON e.EmpleadoID = ee.EmpleadoID
            WHERE ee.ServicioID = ? AND e.RolID = 2
            """;

            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                Empleado e = new Empleado();
                int empleadoId = rs.getInt("EmpleadoID");
                e.setId(rs.getLong("EmpleadoID"));
                e.setNombre(rs.getString("Nomb_Empleado"));
                e.setApellido(rs.getString("Apel_Empleado"));
                e.setEmail(rs.getString("Email_Empleado"));
                e.setTelefono(rs.getString("telefono"));
                e.setEstado(rs.getString("EstadoEmpleado"));
                e.setFotito(rs.getString("FotoEmpleado"));
                e.setRolID(rs.getInt("RolID"));
            
                // obtener ruta desde imagenes-service con fallback a default
                String nombreFoto = e.getFotito();
                if (nombreFoto == null || nombreFoto.isBlank()) {
                    nombreFoto = "default.jpg";
                }
                e.setFotito(imagenesClient.obtenerRutaImagen("trabajadores", nombreFoto));
            
                // cargar especialidades
                e.setEspecialidades(especialidadDAO.listarPorEmpleadoYServicio(empleadoId, servicioId));
                return e;
            }, servicioId);
            
    }
}

