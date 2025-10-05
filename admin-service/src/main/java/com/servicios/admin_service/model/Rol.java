package com.servicios.admin_service.model;

public class Rol {
    private int rolID;
    private String nom_rol;
    private String descrip_rol;
    private String estado_rol;

    public Rol() { }

    // Getters y Setters
    public int getRolID() {
        return rolID;
    }

    public void setRolID(int rolID) {
        this.rolID = rolID;
    }

    public String getNom_rol() {
        return nom_rol;
    }

    public void setNom_rol(String nom_rol) {
        this.nom_rol = nom_rol;
    }

    public String getDescrip_rol() {
        return descrip_rol;
    }

    public void setDescrip_rol(String descrip_rol) {
        this.descrip_rol = descrip_rol;
    }

    public String getEstado_rol() {
        return estado_rol;
    }

    public void setEstado_rol(String estado_rol) {
        this.estado_rol = estado_rol;
    }
}