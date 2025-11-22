package com.servicios.admin_service.model;

public class UsuarioRequest {
    private String nombre;
    private String apellido;
    private String email;
    private String pwd;
    private String rol; 
    protected String estado;
    protected String DNI;
    

    public UsuarioRequest() {}

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPwd() { return pwd; }
    public void setPwd(String pwd) { this.pwd = pwd; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getEstado() {    return estado;    }
    public void setEstado(String estado) {    this.estado = estado;    }

    public String getDNI() {    return DNI;    }
    public void setDNI(String DNI) {    this.DNI = DNI;    }
}
