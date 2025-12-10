package com.servicios.empleado_service.model;

public class CategoriaServicio {

    private Integer categoriaServicioID;
    private String nombre;
    private String descripcion;
    private String estado;

    public Integer getCategoriaServicioID() { return categoriaServicioID; }
    public void setCategoriaServicioID(Integer categoriaServicioID) { this.categoriaServicioID = categoriaServicioID; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
