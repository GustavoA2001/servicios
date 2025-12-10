package com.servicios.empleado_service.service;

import com.servicios.empleado_service.model.ServicioDTO;
import com.servicios.empleado_service.model.Servicio;
import com.servicios.empleado_service.repository.ServicioRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;
import java.io.IOException;
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

    // ----------------------------------------------------------------
    // NUEVO método create: JDBC + DTO + imagen-service
    // ----------------------------------------------------------------
    public void crearServicioDTO(ServicioDTO dto) throws IOException {
        System.out.println("[Service] Iniciando creación de servicio DTO");
    
        // 1. Subimos imagen al microservicio
        String nombreImagen = subirImagen(dto.getFotoServicio());
        System.out.println("[Service] Imagen subida, nombre=" + nombreImagen);
    
        // 2. Insertamos servicio por JDBC (sin EspecialidadID porque no existe en la tabla servicio)
        String sql = """
                INSERT INTO servicio
                (nomb_servicio, precio_servicio, descrip_servicio, CategoriaServicioID, FotoServicio)
                VALUES (?, ?, ?, ?, ?)
                """;
    
        jdbc.update(sql,
                dto.getNombServicio(),
                dto.getPrecioServicio(),
                dto.getDescripServicio(),
                dto.getCategoriaServicioID(),
                nombreImagen);
    
        System.out.println("[Service] Servicio insertado en tabla servicio");
    
        // 3. Obtener ID autogenerado
        Integer servicioID = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        System.out.println("[Service] ID generado=" + servicioID);
    
        // 4. Insertar asignación empleado → servicio (aquí sí va EspecialidadID)
        String sqlAsig = """
                INSERT INTO empleadoespecialidad
                (EmpleadoID, EspecialidadID, ServicioID, FechaRegistro)
                VALUES (?, ?, ?, NOW())
                """;
    
        jdbc.update(sqlAsig,
                dto.getEmpleadoId(),
                dto.getEspecialidadID(),
                servicioID);
    
        System.out.println("[Service] Asignación insertada: empleado=" + dto.getEmpleadoId() + " servicio=" + servicioID);
    }
    
    

    private String subirImagen(MultipartFile imagen) throws IOException {
        if (imagen == null || imagen.isEmpty()) {
            System.out.println("[Service] No se recibió imagen");
            return null;
        }
    
        System.out.println("[Service] Subiendo imagen: " + imagen.getOriginalFilename());
    
        String url = "http://localhost:8090/images";
    
        ByteArrayResource recurso = new ByteArrayResource(imagen.getBytes()) {
            @Override
            public String getFilename() {
                return imagen.getOriginalFilename();
            }
        };
    
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", recurso);
    
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);
    
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
    
        System.out.println("[Service] Respuesta imagenes-service: " + response.getBody());
    
        return (String) response.getBody().get("nombre");
    }
    
    
    
}
