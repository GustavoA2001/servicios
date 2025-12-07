package com.servicios.notificaciones_service.service;

import com.servicios.notificaciones_service.model.EmailRequest;
import com.servicios.notificaciones_service.model.Notificacion;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.sql.SQLException; // importante: para capturar la excepción
import java.util.Map;

@Service
public class CorreoService {

    private final JavaMailSender mailSender;
    private final RestTemplate restTemplate = new RestTemplate();
    private final NotificacionService notificacionService;

    public CorreoService(JavaMailSender mailSender, NotificacionService notificacionService) {
        this.mailSender = mailSender;
        this.notificacionService = notificacionService;
    }

    public void enviarCorreo(EmailRequest emailRequest) {
        String estado;
        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setTo(emailRequest.getDestinatario());
            mensaje.setSubject(emailRequest.getAsunto());
            mensaje.setText(emailRequest.getMensaje());
            mailSender.send(mensaje);
            estado = "ENVIADO";
            System.out.println("[CorreoService] Correo enviado correctamente a: " + emailRequest.getDestinatario());
        } catch (Exception e) {
            estado = "ERROR";
            System.err.println("[CorreoService] Error al enviar correo: " + e.getMessage());
        }
    
        // Guardar notificación persistente
        Notificacion notif = new Notificacion();
        notif.setUsuarioId(null);
        notif.setTipoUsuario("Cliente");
        notif.setTipoNotificacion("PERSISTENTE");
        notif.setCanal("CORREO");
        notif.setMensaje(emailRequest.getAsunto() + ": " + emailRequest.getMensaje());
        notif.setEstado(estado);
    
        System.out.println("[CorreoService] Notificación creada: " + notif);
    
        try {
            notificacionService.enviar(notif);
            System.out.println("[CorreoService] Notificación persistente guardada en BD");
        } catch (SQLException e) {
            System.err.println("[CorreoService] Error al guardar notificación: " + e.getMessage());
        }
    
        // Confirmar al admin-service
        enviarConfirmacionAdminService(emailRequest.getDestinatario(), estado);
    }
    
    private void enviarConfirmacionAdminService(String destinatario, String estado) {
        try {
            String url = "http://localhost:8087/api/callback/confirmarCorreo";
            Map<String, String> body = Map.of("destinatario", destinatario, "estado", estado);
    
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
    
            HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
            restTemplate.postForEntity(url, request, String.class);
    
            System.out.println("[CorreoService] Confirmación enviada al admin-service: " + destinatario + " (" + estado + ")");
        } catch (Exception ex) {
            System.err.println("[CorreoService] No se pudo enviar la confirmación al admin-service: " + ex.getMessage());
        }
    }
}
