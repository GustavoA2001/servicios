package com.servicios.servicios_service.model;

import java.util.List;


public class Servicio {
    private Integer id;                     // ServicioID
    private String titulo;                  // Nomb_Servicio
    private String descripcionCorta;        // Descrip_Servicio
    private String descripcionCompleta;     // opcional, extendido
    private Double precioEstimado;          // Precio_Servicio
    private Integer categoriaServicioId;    // FK a categoriaservicio
    private String categoria;               // nombre de la categoría (join opcional)

    // Campos extendidos (no están en BD, pero útiles en la vista)
    private Double duracionHoras;
    private String telefonoContacto;
    private String imagen;
    private Double valoracion;
    private List<String> etiquetas;
    private Boolean disponible;

    private List<Empleado> empleados;                  // trabajadores asignados
    private List<CalificacionServicio> calificaciones; // calificaciones del servicio
   
    public Servicio() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Integer getCategoriaServicioId() { return categoriaServicioId; }
    public void setCategoriaServicioId(Integer categoriaServicioId) { this.categoriaServicioId = categoriaServicioId; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getDescripcionCorta() { return descripcionCorta; }
    public void setDescripcionCorta(String descripcionCorta) { this.descripcionCorta = descripcionCorta; }

    public String getDescripcionCompleta() { return descripcionCompleta; }
    public void setDescripcionCompleta(String descripcionCompleta) { this.descripcionCompleta = descripcionCompleta; }

    public Double getPrecioEstimado() { return precioEstimado; }
    public void setPrecioEstimado(Double precioEstimado) { this.precioEstimado = precioEstimado; }

    public Double getDuracionHoras() { return duracionHoras; }
    public void setDuracionHoras(Double duracionHoras) { this.duracionHoras = duracionHoras; }

    public String getTelefonoContacto() { return telefonoContacto; }
    public void setTelefonoContacto(String telefonoContacto) { this.telefonoContacto = telefonoContacto; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public Double getValoracion() { return valoracion; }
    public void setValoracion(Double valoracion) { this.valoracion = valoracion; }

    public List<String> getEtiquetas() { return etiquetas; }
    public void setEtiquetas(List<String> etiquetas) { this.etiquetas = etiquetas; }

    public Boolean getDisponible() { return disponible; }
    public void setDisponible(Boolean disponible) { this.disponible = disponible; }
    
    
    public List<Empleado> getEmpleados() {
        return empleados;
    }
    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    public List<CalificacionServicio> getCalificaciones() {
        return calificaciones;
    }
    public void setCalificaciones(List<CalificacionServicio> calificaciones) {
        this.calificaciones = calificaciones;
    }
}

