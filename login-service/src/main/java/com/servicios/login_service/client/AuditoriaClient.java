package com.servicios.login_service.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.servicios.login_service.model.Auditoria;

@Component
public class AuditoriaClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public void enviarAuditoria(Auditoria auditoria) {
        String url = "http://localhost:8091/auditoria";
        restTemplate.postForObject(url, auditoria, Void.class);
    }
}