package com.servicios.empleado_service.model;

import org.springframework.web.multipart.MultipartFile;

public class ServicioDTO {

    private Integer servicioID;
    private Integer empleadoId;

    private String nombServicio;
    private Double precioServicio;
    private String descripServicio;

    private Integer categoriaServicioID;
    private Integer especialidadID;

    // Imagen enviada desde el formulario
    private MultipartFile fotoServicio;

    // Getters y setters

    public Integer getServicioID() {
        return servicioID;
    }

    public void setServicioID(Integer servicioID) {
        this.servicioID = servicioID;
    }

    public Integer getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Integer empleadoId) {
        this.empleadoId = empleadoId;
    }

    public String getNombServicio() {
        return nombServicio;
    }

    public void setNombServicio(String nombServicio) {
        this.nombServicio = nombServicio;
    }

    public Double getPrecioServicio() {
        return precioServicio;
    }

    public void setPrecioServicio(Double precioServicio) {
        this.precioServicio = precioServicio;
    }

    public String getDescripServicio() {
        return descripServicio;
    }

    public void setDescripServicio(String descripServicio) {
        this.descripServicio = descripServicio;
    }

    public Integer getCategoriaServicioID() {
        return categoriaServicioID;
    }

    public void setCategoriaServicioID(Integer categoriaServicioID) {
        this.categoriaServicioID = categoriaServicioID;
    }

    public Integer getEspecialidadID() {
        return especialidadID;
    }

    public void setEspecialidadID(Integer especialidadID) {
        this.especialidadID = especialidadID;
    }

    public MultipartFile getFotoServicio() {
        return fotoServicio;
    }

    public void setFotoServicio(MultipartFile fotoServicio) {
        this.fotoServicio = fotoServicio;
    }
}
