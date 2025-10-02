package com.servicios.admin_service.model;

import java.time.LocalDateTime;

public class Calificaciones {
    
    private int calificaionID;
    private int calificacion;
    private LocalDateTime fechaCalificacion;
    private String comentario;

    private int pedidoID;

    // Getters y Setters

    public int getCalificaionID() {
        return calificaionID;
    }
    public void setCalificaionID(int calificaionID) {
        this.calificaionID = calificaionID;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }
    public int getCalificacion() {
        return calificacion;
    }

    public void setFechaCalificacion(LocalDateTime fechaCalificacion) {
        this.fechaCalificacion = fechaCalificacion;
    }
    public LocalDateTime getFechaCalificacion() {
        return fechaCalificacion;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    public String getComentario() {
        return comentario;
    }

    public void setPedidoID(int pedidoID) {
        this.pedidoID = pedidoID;
    }
    public int getPedidoID() {
        return pedidoID;
    }

}
