package com.servicios.notificaciones_service.service;

import com.servicios.notificaciones_service.model.EmailRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;
@Service
public class CorreoService {

    private final JavaMailSender mailSender;
    private final RestTemplate restTemplate = new RestTemplate();

    public CorreoService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarCorreo(EmailRequest emailRequest) {
        String estado;

        try {
            // Enviar correo
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setTo(emailRequest.getDestinatario());
            mensaje.setSubject(emailRequest.getAsunto());
            mensaje.setText(emailRequest.getMensaje());

            mailSender.send(mensaje);
            estado = "ENVIADO";
            System.out.println("Correo enviado correctamente a " + emailRequest.getDestinatario());
        } catch (Exception e) {
            estado = "ERROR";
            System.err.println("Error al enviar correo: " + e.getMessage());
        }

        // Enviar confirmación al admin-service
        enviarConfirmacionAdminService(emailRequest.getDestinatario(), estado);
    }

    private void enviarConfirmacionAdminService(String destinatario, String estado) {
        try {
            String url = "http://localhost:8087/api/callback/confirmarCorreo"; // Ajusta al puerto real
            Map<String, String> body = new HashMap<>();
            body.put("destinatario", destinatario);
            body.put("estado", estado);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
            restTemplate.postForEntity(url, request, String.class);

            System.out.println(" Confirmación enviada al admin-service: " + destinatario + " (" + estado + ")");
        } catch (Exception ex) {
            System.err.println(" No se pudo enviar la confirmación al admin-service: " + ex.getMessage());
        }
    }
}