package com.servicios.servicios_service.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Pedido {
    private Integer pedidoID;
    private Integer clienteID;
    private Integer servicioID;
    private Integer direccionID;

    private LocalDateTime fechaPedido;     // cuándo se registró el pedido
    private String estadoPedido;           // SOLICITADO, CONFIRMADO, etc.

    private LocalDateTime fechaServicio;   // fecha + hora de inicio del servicio
    private Integer diasServicio;          // cuántos días se repite
    private Integer horasServicio;         // cuántas horas por día
    private Integer duracionPedido;        // horasServicio × diasServicio

    private BigDecimal costoTotal;         // precioHora × horasServicio × diasServicio
    private String observaciones;
    private String bloques;

    // Getters y Setters
    public Integer getPedidoID() {
        return pedidoID;
    }
    public void setPedidoID(Integer pedidoID) {
        this.pedidoID = pedidoID;
    }

    public Integer getClienteID() {
        return clienteID;
    }
    public void setClienteID(Integer clienteID) {
        this.clienteID = clienteID;
    }

    public Integer getServicioID() {
        return servicioID;
    }
    public void setServicioID(Integer servicioID) {
        this.servicioID = servicioID;
    }

    public Integer getDireccionID() {
        return direccionID;
    }
    public void setDireccionID(Integer direccionID) {
        this.direccionID = direccionID;
    }

    public LocalDateTime getFechaPedido() {
        return fechaPedido;
    }
    public void setFechaPedido(LocalDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getEstadoPedido() {
        return estadoPedido;
    }
    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public LocalDateTime getFechaServicio() {
        return fechaServicio;
    }
    public void setFechaServicio(LocalDateTime fechaServicio) {
        this.fechaServicio = fechaServicio;
    }

    public Integer getDiasServicio() {
        return diasServicio;
    }
    public void setDiasServicio(Integer diasServicio) {
        this.diasServicio = diasServicio;
    }

    public Integer getHorasServicio() {
        return horasServicio;
    }
    public void setHorasServicio(Integer horasServicio) {
        this.horasServicio = horasServicio;
    }

    public Integer getDuracionPedido() {
        return duracionPedido;
    }
    public void setDuracionPedido(Integer duracionPedido) {
        this.duracionPedido = duracionPedido;
    }

    public BigDecimal getCostoTotal() {
        return costoTotal;
    }
    public void setCostoTotal(BigDecimal costoTotal) {
        this.costoTotal = costoTotal;
    }

    public String getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getBloques() { return bloques; }
    public void setBloques(String bloques) { this.bloques = bloques; }
}
