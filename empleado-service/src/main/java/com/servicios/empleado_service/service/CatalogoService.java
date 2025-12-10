package com.servicios.empleado_service.service;

import com.servicios.empleado_service.model.CategoriaServicio;
import com.servicios.empleado_service.model.Especialidad;
import com.servicios.empleado_service.repository.CategoriaServicioRepository;
import com.servicios.empleado_service.repository.EspecialidadRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CatalogoService {

    private final CategoriaServicioRepository categoriaRepo;
    private final EspecialidadRepository especialidadRepo;

    public CatalogoService(CategoriaServicioRepository categoriaRepo,
                           EspecialidadRepository especialidadRepo) {
        this.categoriaRepo = categoriaRepo;
        this.especialidadRepo = especialidadRepo;
    }

    public List<CategoriaServicio> listarCategorias() {
        return categoriaRepo.listar();
    }

    public List<Especialidad> listarEspecialidades() {
        return especialidadRepo.listar();
    }
}
