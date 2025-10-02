package com.servicios.admin_service.model;

public class Servicio {
    
    private int servicioId;
    private String nomb_Servicio;
    private String descrip_Servicio;
    private double precio_Servicio;

    private CategoriaServicio categoria = new CategoriaServicio();  // Inicializamos aqui para no hacerlo en el constructor del servicioDAO
    private Empleado empleado = new Empleado();  // Inicializamos aqui para no hacerlo en el constructor del servicioDAO

    public Servicio() {}

    // getters y setters
    public int getServicioId() { return servicioId; }
    public void setServicioId(int servicioId) { this.servicioId = servicioId; }

    public String getNomb_Servicio() { return nomb_Servicio; }
    public void setNomb_Servicio(String nomb_Servicio) { this.nomb_Servicio = nomb_Servicio; }

    public String getDescrip_Servicio() { return descrip_Servicio; }
    public void setDescrip_Servicio(String descrip_Servicio) { this.descrip_Servicio = descrip_Servicio; }

    public double getPrecio_Servicio() { return precio_Servicio; }
    public void setPrecio_Servicio(double precio_Servicio) { this.precio_Servicio = precio_Servicio; }

    public CategoriaServicio getCategoria() { return categoria; }
    public void setCategoria(CategoriaServicio categoria) { this.categoria = categoria; }

    public Empleado getEmpleado() { return empleado; }
    public void setEmpleado(Empleado empleado) { this.empleado = empleado; }

}
