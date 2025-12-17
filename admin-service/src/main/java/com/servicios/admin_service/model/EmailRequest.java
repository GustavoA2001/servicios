package com.servicios.admin_service.model;

public class EmailRequest {

    private String destinatario;
    private String asunto;
    private String mensaje;

    // ===== Constructores =====
    public EmailRequest() {}

    public EmailRequest(String destinatario, String asunto, String mensaje) {
        this.destinatario = destinatario;
        this.asunto = asunto;
        this.mensaje = mensaje;
    }

    // ===== Getters y Setters =====
    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    // ===== toString =====
    @Override
    public String toString() {
        return "EmailRequest{" +
                "destinatario='" + destinatario + '\'' +
                ", asunto='" + asunto + '\'' +
                ", mensaje='" + mensaje + '\'' +
                '}';
    }
}
