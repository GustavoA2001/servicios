package com.servicios.servicios_service.model;

public class Distrito {
    
    private int distritoID;
    private String nomb_Distrito;
    private String estado_Distrito;

    public Distrito() {}

    public Distrito(int distritoID, String nomb_Distrito) {
        this.distritoID = distritoID;
        this.nomb_Distrito = nomb_Distrito;
    }

    // Getters y Setters
    public int getDistritoID() {     return distritoID;     }
    public void setDistritoID(int distritoID) {     this.distritoID = distritoID;     }

    public String getNomb_Distrito() {     return nomb_Distrito;     }
    public void setNomb_Distrito(String nomb_Distrito) {     this.nomb_Distrito = nomb_Distrito;     }

    public String getEstado_Distrito() {    return estado_Distrito;    }
    public void setEstado_Distrito(String estado_Distrito) {    this.estado_Distrito = estado_Distrito;    }
    

}
