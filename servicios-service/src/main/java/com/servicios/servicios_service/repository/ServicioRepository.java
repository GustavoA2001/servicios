package com.servicios.servicios_service.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.servicios.servicios_service.model.Servicio;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ServicioRepository {

    private List<Servicio> servicios = new ArrayList<>();

    public ServicioRepository() {
        loadData();
    }

    private void loadData() {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = new ClassPathResource("data/servicios.json").getInputStream()) {
            servicios = mapper.readValue(is, new TypeReference<List<Servicio>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            servicios = new ArrayList<>();
        }
    }

    public List<Servicio> findAll() {
        return servicios;
    }

    public Optional<Servicio> findById(Integer id) {
        return servicios.stream().filter(s -> s.getId().equals(id)).findFirst();
    }
}
