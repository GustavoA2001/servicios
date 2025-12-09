package com.servicios.servicios_service.model;

public class DireccionCliente {

    private Integer idDireccion;
    private String direccion;
    private String referencia;
    private String estadoDireccion;
    private Integer clienteId;
    private Integer distritoId;
    private String distrito; // nombre del distrito (join opcional)

    public Integer getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(Integer idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getEstadoDireccion() {
        return estadoDireccion;
    }

    public void setEstadoDireccion(String estadoDireccion) {
        this.estadoDireccion = estadoDireccion;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getDistritoId() {
        return distritoId;
    }

    public void setDistritoId(Integer distritoId) {
        this.distritoId = distritoId;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }
}
