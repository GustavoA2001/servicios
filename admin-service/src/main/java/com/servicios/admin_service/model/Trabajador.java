package com.servicios.admin_service.model;

import java.time.LocalDateTime; 


public class Trabajador extends Usuario {


    public Trabajador() {
    }

    public Trabajador(Long id, String nombre, String apellido, String email, String pwd, String estado, String DNI,
            String direccion, String telefono, String fotito, LocalDateTime fechaRegistro) {
        super(id, nombre, apellido, email, pwd, estado, DNI, fotito, fechaRegistro); // llama al constructor de Usuario

    }

}
