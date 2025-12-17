package com.servicios.admin_service.model;

public class AuditoriaMapper { 
    public static AuditoriaDTO fromUsuario(Usuario u, boolean incluirExtras) { 
        if (u == null) return null; return new AuditoriaDTO(u, incluirExtras); 
    } 
}