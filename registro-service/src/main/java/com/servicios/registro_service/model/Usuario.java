package com.servicios.registro_service.model;

public class Usuario {
    private Long id;         // generado automáticamente, no enviado desde frontend
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String dni;
    private String estado;   // ACTIVO / INACTIVO
    private String fechaRegistro;

    private Long id_rol;     // se asigna según el rol
    private String rol;      // CLIENTE, EMPLEADO, ADMIN

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public Long getId_rol() { return id_rol; }
    public void setId_rol(Long id_rol) { this.id_rol = id_rol; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(String fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}