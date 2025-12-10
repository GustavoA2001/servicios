package com.servicios.empleado_service.service;

import com.servicios.empleado_service.model.Servicio;
import com.servicios.empleado_service.repository.ServicioRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ServicioService {

    private final ServicioRepository repo;
    private final JdbcTemplate jdbc;

    public ServicioService(ServicioRepository repo, JdbcTemplate jdbc) {
        this.repo = repo;
        this.jdbc = jdbc;
    }

    public List<Servicio> listAll() {
        return repo.findAll();
    }

    public List<Servicio> listByEmpleado(Integer empleadoId) {
        if (empleadoId == null || empleadoId == 0) {
            return repo.findAll();
        }
        return repo.findByEmpleadoId(empleadoId);
    }

    public Servicio get(Integer id) {
        Optional<Servicio> o = repo.findById(id);
        return o.orElse(null);
    }

    public Servicio save(Servicio s) {
        return repo.save(s);
    }

    public Servicio saveAndAssign(Servicio s, Integer empleadoId) {
        Servicio saved = repo.save(s);
        if (empleadoId != null && empleadoId > 0 && saved.getServicioID() != null) {
            String sql = "INSERT INTO empleadoespecialidad (EmpleadoID, EspecialidadID, ServicioID, FechaRegistro) VALUES (?, NULL, ?, NOW())";
            jdbc.update(sql, empleadoId, saved.getServicioID());
        }
        return saved;
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    public int removeAssignment(Integer empleadoId, Integer servicioId) {
        String sql = "DELETE FROM empleadoespecialidad WHERE EmpleadoID = ? AND ServicioID = ?";
        return jdbc.update(sql, empleadoId, servicioId);
    }
}
