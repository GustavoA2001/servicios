package com.servicios.notificaciones_service.service;

import com.servicios.notificaciones_service.model.EmailRequest;
import com.servicios.notificaciones_service.model.Notificacion;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.sql.SQLException; // importante: para capturar la excepción
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class CorreoService {

    private final JavaMailSender mailSender; // componente de Spring para enviar correos
    private final RestTemplate restTemplate = new RestTemplate(); // cliente HTTP para callback
    private final NotificacionService notificacionService; // servicio para persistir notificaciones

    public CorreoService(JavaMailSender mailSender, NotificacionService notificacionService) {
        this.mailSender = mailSender;
        this.notificacionService = notificacionService;
    }

    // Envía un correo y registra la notificación en BD
    public void enviarCorreo(EmailRequest emailRequest) {
        String estado;
        try {
            // Construcción del correo
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setTo(emailRequest.getDestinatario());
            mensaje.setSubject(emailRequest.getAsunto());
            mensaje.setText(emailRequest.getMensaje());

            // Envío del correo
            mailSender.send(mensaje);
            estado = "ENVIADO";
            System.out.println("[CorreoService] Correo enviado correctamente a: " + emailRequest.getDestinatario());
        } catch (Exception e) {
            estado = "ERROR";
            System.err.println("[CorreoService] Error al enviar correo: " + e.getMessage());
        }

        // Crear objeto Notificacion para persistencia
        Notificacion notif = new Notificacion();
        notif.setUsuarioId(null); // puede ligarse a un usuario si se conoce
        notif.setTipoUsuario("Cliente");
        notif.setTipoNotificacion("PERSISTENTE"); // se guarda en BD
        notif.setCanal("CORREO");
        notif.setMensaje(emailRequest.getAsunto() + ": " + emailRequest.getMensaje());
        notif.setEstado(estado);
        notif.setFechaCreacion(LocalDateTime.now());

        // Guardar notificación en BD
        try {
            notificacionService.enviar(notif);
            System.out.println("[CorreoService] Notificación persistente guardada en BD");
        } catch (SQLException e) {
            System.err.println("[CorreoService] Error al guardar notificación: " + e.getMessage());
        }

        // Enviar confirmación al admin-service (callback)
        enviarConfirmacionAdminService(emailRequest.getDestinatario(), estado);
    }

    // Callback hacia admin-service para confirmar estado del correo
    private void enviarConfirmacionAdminService(String destinatario, String estado) {
        try {
            String url = "http://localhost:8087/api/callback/confirmarCorreo";
            Map<String, String> body = Map.of("destinatario", destinatario, "estado", estado);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
            restTemplate.postForEntity(url, request, String.class);

            System.out.println(
                    "[CorreoService] Confirmación enviada al admin-service: " + destinatario + " (" + estado + ")");
        } catch (Exception ex) {
            System.err
                    .println("[CorreoService] No se pudo enviar la confirmación al admin-service: " + ex.getMessage());
        }
    }
}
