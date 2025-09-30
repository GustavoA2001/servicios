package com.servicios.admin_service.model;

import java.time.LocalDateTime; 


public class Cliente extends Usuario {

    /* 
     * Cliente
     * DEBO AGREGAR EL CONTACTO DEL CLIENTE
     */


    private Direccion direccion;

    public Cliente() {
    }

    public Cliente(Long id, String nombre, String apellido, String email, String pwd, String estado, String DNI,
            String direccion, String telefono, String fotito, LocalDateTime fechaRegistro) {
        super(id, nombre, apellido, email, pwd, estado, DNI, fotito, fechaRegistro);

    }
 
    
}
