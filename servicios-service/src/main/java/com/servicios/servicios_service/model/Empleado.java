package com.servicios.servicios_service.model;

import java.util.List;

public class Empleado {

    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String estado;
    private String fotito;
    private int rolID;

    private List<Especialidad> especialidades;
    
    // Constructor vacío
    public Empleado() {
    }

    // Constructor con parámetros (opcional)
    public Empleado(Long id, String nombre, String apellido, String email, String telefono,
                    String estado, String fotito, int rolID) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.estado = estado;
        this.fotito = fotito;
        this.rolID = rolID;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFotito() {
        return fotito;
    }

    public void setFotito(String fotito) {
        this.fotito = fotito;
    }

    public int getRolID() {
        return rolID;
    }

    public void setRolID(int rolID) {
        this.rolID = rolID;
    }

        // getters y setters
        public List<Especialidad> getEspecialidades() { return especialidades; }
        public void setEspecialidades(List<Especialidad> especialidades) { this.especialidades = especialidades; }
}
