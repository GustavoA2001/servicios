package com.servicios.empleado_service.repository;

import com.servicios.empleado_service.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServicioRepository extends JpaRepository<Servicio, Integer> {

    @Query(value = "SELECT s.* FROM servicio s " +
                   "INNER JOIN empleadoespecialidad ee ON s.ServicioID = ee.ServicioID " +
                   "WHERE ee.EmpleadoID = :empleadoId", nativeQuery = true)
    List<Servicio> findByEmpleadoId(@Param("empleadoId") Integer empleadoId);
}
