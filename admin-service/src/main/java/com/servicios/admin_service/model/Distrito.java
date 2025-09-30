package com.servicios.admin_service.model;

public class Distrito {
    
    private int distritoID;
    private String nombre;

    public Distrito() {}

    public Distrito(int distritoID, String nombre) {
        this.distritoID = distritoID;
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getDistritoID() { return distritoID; }
    public void setDistritoID(int distritoID) { this.distritoID = distritoID; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
