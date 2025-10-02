package com.servicios.admin_service.model;

import java.time.LocalDateTime;

public class Pedido {

    private int pedidoID;
    private LocalDateTime fechaPedido;
    private String estadoPedido;
    private int duracionPedido;
    private double costoTotal;

    public Cliente cliente = new Cliente();  // Inicializamos aqui para no hacerlo en el constructor del pedidoDAO

    public Pedido() { }

    public Pedido(int pedidoID, LocalDateTime fechaPedido, String estadoPedido, int duracionPedido, double costoTotal) {
        this.pedidoID = pedidoID;
        this.fechaPedido = fechaPedido;
        this.estadoPedido = estadoPedido;
        this.duracionPedido = duracionPedido;
        this.costoTotal = costoTotal;
    }
    
    public int getPedidoID() { return pedidoID; }
    public void setPedidoID(int pedidoID) { this.pedidoID = pedidoID; }

    public LocalDateTime getFechaPedido() { return fechaPedido; }
    public void setFechaPedido(LocalDateTime fechaPedido) { this.fechaPedido = fechaPedido; }

    public String getEstadoPedido() { return estadoPedido; }
    public void setEstadoPedido(String estadoPedido) { this.estadoPedido = estadoPedido; }

    public int getDuracionPedido() { return duracionPedido; }
    public void setDuracionPedido(int duracionPedido) { this.duracionPedido = duracionPedido; }

    public double getCostoTotal() { return costoTotal; }
    public void setCostoTotal(double costoTotal) { this.costoTotal = costoTotal; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

}
