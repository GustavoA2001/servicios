package com.servicios.admin_service.model;

public class InformePagoDTO extends Pago {

    
    private String nombreCliente;
    private String servicioContratado;
    private String nombreEmpleado;

    // Si quieres enlaces en la vista, añade también:
    private Long empleadoId;

    // Getters y Setters
    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getServicioContratado() { return servicioContratado; }
    public void setServicioContratado(String servicioContratado) { this.servicioContratado = servicioContratado; }

    public String getNombreEmpleado() { return nombreEmpleado; }
    public void setNombreEmpleado(String nombreEmpleado) { this.nombreEmpleado = nombreEmpleado; }

    public Long getEmpleadoId() { return empleadoId; }
    public void setEmpleadoId(Long empleadoId) { this.empleadoId = empleadoId; }
}
