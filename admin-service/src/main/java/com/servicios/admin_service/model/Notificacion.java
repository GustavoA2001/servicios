package com.servicios.admin_service.model;

import java.time.LocalDateTime;

public class Notificacion {

    private Long notificacionId;
    private Long usuarioId;
    private Long pedidoId;
    private String tipoUsuario;       // Cliente, Admin, etc.
    private String tipoNotificacion;  // PERSISTENTE, GLOBAL, SIMPLE
    private String canal;             // CORREO, SISTEMA, PUSH
    private String mensaje;
    private String estado;            // ENVIADO, ERROR, PENDIENTE
    private LocalDateTime fechaCreacion;

    // ===== Constructores =====
    public Notificacion() {}

    public Notificacion(Long usuarioId, String tipoUsuario, String tipoNotificacion,
                        String canal, String mensaje, String estado, LocalDateTime fechaCreacion) {
        this.usuarioId = usuarioId;
        this.tipoUsuario = tipoUsuario;
        this.tipoNotificacion = tipoNotificacion;
        this.canal = canal;
        this.mensaje = mensaje;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
    }

    // ===== Getters y Setters =====
    public Long getNotificacionId() {
        return notificacionId;
    }

    public void setNotificacionId(Long notificacionId) {
        this.notificacionId = notificacionId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getTipoNotificacion() {
        return tipoNotificacion;
    }

    public void setTipoNotificacion(String tipoNotificacion) {
        this.tipoNotificacion = tipoNotificacion;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    // ===== toString =====
    @Override
    public String toString() {
        return "Notificacion{" +
                "notificacionId=" + notificacionId +
                ", usuarioId=" + usuarioId +
                ", pedidoId=" + pedidoId +
                ", tipoUsuario='" + tipoUsuario + '\'' +
                ", tipoNotificacion='" + tipoNotificacion + '\'' +
                ", canal='" + canal + '\'' +
                ", mensaje='" + mensaje + '\'' +
                ", estado='" + estado + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}
