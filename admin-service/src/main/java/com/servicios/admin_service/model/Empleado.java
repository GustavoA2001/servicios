package com.servicios.admin_service.model;

import java.time.LocalDateTime;

public class Empleado extends Usuario {

    private String telefono;
    private String ubicacionPartida;
    private int rolID;

    public Empleado() {
    }

    public Empleado(Long id, String nombre, String apellido, String email, String pwd, String estado, String DNI,
            String direccion, String telefono, String fotito, LocalDateTime fechaRegistro) {
        super(id, nombre, apellido, email, pwd, estado, DNI, fotito, fechaRegistro); // llama al constructor de Usuario
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getTelefono() {
        return telefono;
    }

    public void setRolID(int rolID) {
        this.rolID = rolID;
    }
    public int getRolID() {
        return rolID;
    }

    public void setUbicacionPartida(String ubicacionPartida) {
        this.ubicacionPartida = ubicacionPartida;
    }
    public String getUbicacionPartida() {
        return ubicacionPartida;
    }

}
