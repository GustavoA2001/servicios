package com.servicios.admin_service.model;

public class Direccion {
    /*
     * DIRECCION 
     */
    int direccionID;
    String direccion;
    String referencia;
    String estadoDireccion;

    /*
     * DISTRITO
     */
    private Distrito distrito; 
    // composición con el DISTRITO, una dirección tiene un distrito

    /*
     * CONSTRUCTORES
     */
    public Direccion() { }

    public Direccion(int direccionID, String direccion, String referencia, String estadoDireccion) {
        this.direccionID = direccionID;
        this.direccion = direccion;
        this.referencia = referencia;
        this.estadoDireccion = estadoDireccion;
    }

    // Getters y Setters
    public int getDireccionID() { return direccionID; }
    public void setDireccionID(int direccionID) { this.direccionID = direccionID; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getReferencia() { return referencia; }
    public void setReferencia(String referencia) { this.referencia = referencia; }

    public String getEstadoDireccion() { return estadoDireccion; }
    public void setEstadoDireccion(String estadoDireccion) { this.estadoDireccion = estadoDireccion; }

    public Distrito getDistrito() { return distrito; }
    public void setDistrito(Distrito distrito) { this.distrito = distrito; }
    

}
