package com.servicios.admin_service.model;

import java.time.LocalDateTime;

public class Administrador extends Usuario {
    private String codigoAdmin;
    private String telefono;

    public Administrador() {}

    public Administrador(Long id, String nombre, String apellido, String email, String pwd, String estado, String DNI,
            String direccion, String telefono, String fotito, LocalDateTime fechaRegistro) {
        super(id, nombre, apellido, email, pwd, estado, DNI, fotito, fechaRegistro); // llama al constructor de Usuario
    }


    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCodigoAdmin() { return codigoAdmin; }
    public void setCodigoAdmin(String codigoAdmin) { this.codigoAdmin = codigoAdmin; }
}
