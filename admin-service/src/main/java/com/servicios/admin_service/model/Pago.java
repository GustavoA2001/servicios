package com.servicios.admin_service.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Pago {

    private Long pagoId;
    private Integer pedidoId;
    private Integer clienteId;
    private String metodoPago;  
    private BigDecimal monto;
    private String moneda = "PEN";   // Siempre SOLES por defecto
    private String estadoPago;
    private String paypalOrderId;
    private String paypalCaptureId;
    private String tipoTarjeta;
    private String ultimosDigitos;
    private LocalDateTime fechaPago;
    private String detallesJson;

    // Getters y Setters

    public Long getPagoId() { return pagoId; }
    public void setPagoId(Long pagoId) { this.pagoId = pagoId; }

    public Integer getPedidoId() { return pedidoId; }
    public void setPedidoId(Integer pedidoId) { this.pedidoId = pedidoId; }

    public Integer getClienteId() { return clienteId; }
    public void setClienteId(Integer clienteId) { this.clienteId = clienteId; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public String getMoneda() { return moneda; }

    // Fuerza la moneda a SOLES sin importar lo que venga del servicio de pagos
    public void setMoneda(String moneda) {
        this.moneda = "PEN"; 
    }

    public String getEstadoPago() { return estadoPago; }
    public void setEstadoPago(String estadoPago) { this.estadoPago = estadoPago; }

    public String getPaypalOrderId() { return paypalOrderId; }
    public void setPaypalOrderId(String paypalOrderId) { this.paypalOrderId = paypalOrderId; }

    public String getPaypalCaptureId() { return paypalCaptureId; }
    public void setPaypalCaptureId(String paypalCaptureId) { this.paypalCaptureId = paypalCaptureId; }

    public String getTipoTarjeta() { return tipoTarjeta; }
    public void setTipoTarjeta(String tipoTarjeta) { this.tipoTarjeta = tipoTarjeta; }

    public String getUltimosDigitos() { return ultimosDigitos; }
    public void setUltimosDigitos(String ultimosDigitos) { this.ultimosDigitos = ultimosDigitos; }

    public LocalDateTime getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDateTime fechaPago) { this.fechaPago = fechaPago; }

    public String getDetallesJson() { return detallesJson; }
    public void setDetallesJson(String detallesJson) { this.detallesJson = detallesJson; }
}
