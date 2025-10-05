package com.servicios.admin_service.model;

public class Especialidad {
    private int especialidadID;
    private String nomb_especialidad;
    private String descrip_especialidad;
    private String estado_especialidad;

    public Especialidad() {    }

    // Getter y Setter
    public int getEspecialidadID() {    return especialidadID;    }
    public void setEspecialidadID(int especialidadID) {    this.especialidadID = especialidadID;    }

    public String getNomb_especialidad() {    return nomb_especialidad;    }
    public void setNomb_especialidad(String nomb_especialidad) {    this.nomb_especialidad = nomb_especialidad;    }

    public String getDescrip_especialidad() {    return descrip_especialidad;    }
    public void setDescrip_especialidad(String descrip_especialidad) {    this.descrip_especialidad = descrip_especialidad;    }

    public String getEstado_especialidad() {    return estado_especialidad;    }
    public void setEstado_especialidad(String estado_especialidad) {    this.estado_especialidad = estado_especialidad;    }

}
