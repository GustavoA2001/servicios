package com.servicios.admin_service.model;


public class AuditoriaDTO { 
    private Long id; 
    private String nombre; 
    private String apellido; 
    private String email; 
    private String estado; 
    private String fotito; 
    private String dni; 
    private String rutaFoto; 

    public AuditoriaDTO(Usuario u, boolean incluirExtras) { 
        if (u == null) return; 
        this.id = u.getId(); 
        this.nombre = u.getNombre(); 
        this.apellido = u.getApellido(); 
        this.email = u.getEmail(); 
        this.estado = u.getEstado(); 
        if (incluirExtras) { 
            this.fotito = u.getFotito(); 
            this.dni = u.getDNI(); 
            this.rutaFoto = u.getRutaFoto(); 
        } 
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getFotito() { return fotito; }
    public void setFotito(String fotito) { this.fotito = fotito; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getRutaFoto() { return rutaFoto; }
    public void setRutaFoto(String rutaFoto) { this.rutaFoto = rutaFoto; }

}
