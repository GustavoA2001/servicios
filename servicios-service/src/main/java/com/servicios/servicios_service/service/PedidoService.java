package com.servicios.servicios_service.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.servicios.servicios_service.dao.PedidoDAO;
import com.servicios.servicios_service.model.Pedido;

@Service
public class PedidoService {

    @Autowired
    private PedidoDAO pedidoDAO;

    @Autowired
    private ServicioService servicioService;

    public void guardarPedido(Pedido pedido) {
        pedidoDAO.insertarPedido(pedido);
    }

    public void registrarPedido(Pedido pedido) {
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setEstadoPedido("SOLICITADO");
    
        BigDecimal precioHora = servicioService.obtenerPrecio(pedido.getServicioID());
        BigDecimal costoTotal = precioHora
                .multiply(BigDecimal.valueOf(pedido.getHorasServicio()))
                .multiply(BigDecimal.valueOf(pedido.getDiasServicio()));
        pedido.setCostoTotal(costoTotal);
    
        pedido.setDuracionPedido(pedido.getDiasServicio() * pedido.getHorasServicio());
    
        // Calcular bloques y guardarlos en JSON
        List<Map<String, Object>> bloques = new ArrayList<>();
        LocalDate fechaInicio = pedido.getFechaServicio().toLocalDate();
        LocalTime horaInicio = pedido.getFechaServicio().toLocalTime();
    
        for (int i = 0; i < pedido.getDiasServicio(); i++) {
            LocalDate fecha = fechaInicio.plusDays(i);
            LocalDateTime inicio = LocalDateTime.of(fecha, horaInicio);
            LocalDateTime fin = inicio.plusHours(pedido.getHorasServicio());
    
            Map<String, Object> bloque = new HashMap<>();
            bloque.put("dia", fecha.toString());
            bloque.put("inicio", inicio.toString());
            bloque.put("fin", fin.toString());
            bloques.add(bloque);
        }
    
        try {
            ObjectMapper mapper = new ObjectMapper();
            String bloquesJson = mapper.writeValueAsString(bloques);
            pedido.setBloques(bloquesJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        pedidoDAO.insertarPedido(pedido);
    }
    
    public List<Pedido> listarPedidosPorCliente(int clienteId) {
        return pedidoDAO.listarPedidosPorCliente(clienteId);
    }

    public void actualizarEstado(Integer pedidoId, String nuevoEstado) {
        pedidoDAO.actualizarEstado(pedidoId, nuevoEstado);
    }
    
}
