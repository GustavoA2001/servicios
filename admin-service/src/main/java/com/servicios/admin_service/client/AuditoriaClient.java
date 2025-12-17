package com.servicios.admin_service.client;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.servicios.admin_service.model.Auditoria;

@Component
public class AuditoriaClient {

    // RestTemplate se utiliza para realizar llamadas HTTP a otros microservicios.
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String BASE_URL = "http://localhost:8091/auditoria";
    /**
     * Envía un objeto Auditoria al microservicio de auditoría.
     * Este método actúa como "cliente" que consume el endpoint remoto.
     */

    public void enviarAuditoria(Auditoria auditoria) {
        try {
            System.out.println("[AuditoriaClient] POST -> " + BASE_URL + " payload=" + auditoria);
            restTemplate.postForEntity(BASE_URL, auditoria, Void.class);
            System.out.println("[AuditoriaClient] POST OK");
        } catch (Exception ex) {
            System.out.println("[AuditoriaClient] POST FAILED: " + ex.getMessage());
        }
    }
    

    public List<Auditoria> obtenerAuditorias() {
        Auditoria[] auditorias = restTemplate.getForObject(BASE_URL, Auditoria[].class);
        return Arrays.asList(auditorias);
    }

    public List<Auditoria> obtenerAuditoriasFiltradas(String orderBy, String direction, LocalDate fecha,
            String usuarioTipo, Long usuarioId, String servicio, String accion) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl("http://localhost:8091/auditoria/filtrar")
                .queryParam("orderBy", orderBy)
                .queryParam("direction", direction);

        if (fecha != null) builder.queryParam("fecha", fecha);
        if (usuarioTipo != null) builder.queryParam("usuarioTipo", usuarioTipo);
        if (usuarioId != null) builder.queryParam("usuarioId", usuarioId);
        if (servicio != null) builder.queryParam("servicio", servicio);
        if (accion != null) builder.queryParam("accion", accion);

        Auditoria[] auditorias = restTemplate.getForObject(builder.toUriString(), Auditoria[].class);
        return Arrays.asList(auditorias);
    }

    public List<Auditoria> obtenerAuditoriasPorFecha(LocalDate fecha) {
        String url = BASE_URL +"/filtrar?fecha=" + fecha;
        Auditoria[] auditorias = restTemplate.getForObject(url, Auditoria[].class);
        return Arrays.asList(auditorias);
    }
}
