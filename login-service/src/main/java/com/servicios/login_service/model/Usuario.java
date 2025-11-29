package com.servicios.login_service.model;

public class Usuario {
    private Long id;
    private String email;
    private String password;

    private long id_rol;
    private String rol;

    // Getters y Setters
    public Long getId() {        return id;    }
    public void setId(Long id) {        this.id = id;    }

    public String getEmail() {        return email;    }
    public void setEmail(String email) {        this.email = email;    }

    public String getPassword() {        return password;    }
    public void setPassword(String password) {        this.password = password;    }

    public long getId_rol() {        return id_rol;    }
    public void setId_rol(long id_rol) {        this.id_rol = id_rol;    }

    public String getRol() {        return rol;    }
    public void setRol(String rol) {        this.rol = rol;    }
    
}
