package com.servicios.servicios_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servicios.servicios_service.dao.PedidoDAO;
import com.servicios.servicios_service.model.Pedido;

@Service
public class PedidoService {

    @Autowired
    private PedidoDAO pedidoDAO;

    public void guardarPedido(Pedido pedido) {
        pedidoDAO.insertarPedido(pedido);
    }
}
