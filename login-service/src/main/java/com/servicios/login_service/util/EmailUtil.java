package com.servicios.login_service.util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailUtil {

    // Cambia estos valores por tu cuenta y app password
    private static final String SMTP_USER = "pierrekrrera@gmail.com";
    private static final String SMTP_APP_PASSWORD = "bnmpuotyuimljluk"; // 16 chars (app password)

    public static void enviarCodigoVerificacion(String destino, String codigo) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USER, SMTP_APP_PASSWORD);
            }
        });

        // Habilita debug para ver la conversación SMTP en la consola/log de Tomcat
        session.setDebug(true);

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(SMTP_USER));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destino));
        message.setSubject("Código de verificación - NOVA'S TRAVELS");
        String body = "Tu código de verificación es: " + codigo + "\n\n"
                + "Si no solicitaste este código, ignora este correo.";
        message.setText(body);

        Transport.send(message);
    }
}
