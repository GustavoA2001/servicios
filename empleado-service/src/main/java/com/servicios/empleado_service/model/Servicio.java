package com.servicios.empleado_service.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "servicio")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ServicioID")
    private Integer servicioID;

    @Column(name = "Nomb_Servicio")
    private String nombServicio;

    @Column(name = "Descrip_Servicio")
    private String descripServicio;

    @Column(name = "Precio_Servicio")
    private BigDecimal precioServicio;

    @Column(name = "CategoriaServicioID")
    private Integer categoriaServicioID;

    @Column(name = "FotoServicio")
    private String fotoServicio; // Nuevo campo

    public Integer getServicioID() { return servicioID; }
    public void setServicioID(Integer servicioID) { this.servicioID = servicioID; }

    public String getNombServicio() { return nombServicio; }
    public void setNombServicio(String nombServicio) { this.nombServicio = nombServicio; }

    public String getDescripServicio() { return descripServicio; }
    public void setDescripServicio(String descripServicio) { this.descripServicio = descripServicio; }

    public BigDecimal getPrecioServicio() { return precioServicio; }
    public void setPrecioServicio(BigDecimal precioServicio) { this.precioServicio = precioServicio; }

    public Integer getCategoriaServicioID() { return categoriaServicioID; }
    public void setCategoriaServicioID(Integer categoriaServicioID) { this.categoriaServicioID = categoriaServicioID; }

    public String getFotoServicio() { return fotoServicio; }
    public void setFotoServicio(String fotoServicio) { this.fotoServicio = fotoServicio; }
}
