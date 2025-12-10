package com.servicios.empleado_service.service;

import com.servicios.empleado_service.model.Empleado;
import com.servicios.empleado_service.repository.EmpleadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class EmpleadoService {

    private final EmpleadoRepository repo;

    public EmpleadoService(EmpleadoRepository repo) {
        this.repo = repo;
    }

    public Optional<Empleado> findById(Integer id) {
        return repo.findById(id);
    }

    public Empleado save(Empleado e) {
        return repo.save(e);
    }
}
