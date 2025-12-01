package com.servicios.login_service.util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailUtil {

    private static final String SMTP_USER = "pierrekrrera@gmail.com";
    private static final String SMTP_APP_PASSWORD = "bnmpuotyuimljluk";

    public static void enviarCodigoVerificacion(String destino, String codigo) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); 
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.timeout", "10000");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");


        props.put("mail.smtp.ssl.protocols", "TLSv1.2 TLSv1.3");

        System.setProperty("jdk.tls.client.protocols", "TLSv1.3,TLSv1.2");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USER, SMTP_APP_PASSWORD);
            }
        });

        session.setDebug(true);

        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(SMTP_USER, "NOVA'S TRAVELS"));
        } catch (java.io.UnsupportedEncodingException e) {
            message.setFrom(new InternetAddress(SMTP_USER));
        }
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destino, false));
        message.setSubject("Código de verificación - NOVA'S TRAVELS");
        String body = "Tu código de verificación es: " + codigo + "\n\nSi no solicitaste este código, ignora este correo.";
        message.setText(body);

        Transport.send(message);
    }
}
