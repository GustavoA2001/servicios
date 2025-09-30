package com.servicios.admin_service.model;

import java.time.LocalDateTime; 

public abstract class Usuario {

    protected Long id;
    protected String nombre;
    protected String apellido;
    protected String email;
    protected String pwd;
    protected String estado;
    protected String DNI;
    protected String fotito;
    private LocalDateTime fechaRegistro;

    // Constructor vacío
    public Usuario() {}

    // Constructor con parámetros
    public Usuario(Long id, String nombre, String apellido, String email, String pwd, String estado, String DNI, String fotito, LocalDateTime fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.pwd = pwd;
        this.estado = estado;
        this.DNI = DNI;
        this.fotito = fotito;
    }


    // Getters y Setters
    public Long getId() {    return id;    }
    public void setId(Long id) {    this.id = id;    }

    public String getNombre() {    return nombre;    }
    public void setNombre(String nombre) {    this.nombre = nombre;    }

    public String getApellido() {    return apellido;    }
    public void setApellido(String apellido) {    this.apellido = apellido;    }

    public String getEmail() {    return email;    }
    public void setEmail(String email) {    this.email = email;    }

    public String getPwd() {    return pwd;    }
    public void setPwd(String pwd){    this.pwd = pwd;    }

    public String getEstado() {    return estado;    }
    public void setEstado(String estado) {    this.estado = estado;    }

    public String getDNI() {    return DNI;    }
    public void setDNI(String DNI) {    this.DNI = DNI;    }

    public LocalDateTime getFechaRegistro() {    return fechaRegistro;    }
    public void setFechaRegistro(LocalDateTime fechaRegistro) {    this.fechaRegistro = fechaRegistro;    }

    public String getFotito() {    
        return fotito;    
    }
    public void setFotito(String fotito) {    
        this.fotito = fotito;    
    }

    public String getRutaFoto() {
        if (fotito != null && !fotito.trim().isEmpty()) {
            return "/upload/usuarios/" + fotito;
        }
        return "/img/default-user.png";
    }
    
}
