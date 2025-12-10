package com.servicios.admin_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.servicios.admin_service.client.PedidoClient;
import com.servicios.admin_service.dao.UsuarioDAO;
import com.servicios.admin_service.model.Cliente;
import com.servicios.admin_service.model.Pedido;

@Controller
@RequestMapping("/admin/pedidos")
public class AdminPedidoController {

    @Autowired
    private PedidoClient pedidoClient;

    @Autowired
    private UsuarioDAO usuarioDAO;

    @GetMapping
    public String pedidosForm(Model model) {

        List<Cliente> clientes = usuarioDAO.obtenerClientes();

        model.addAttribute("clientes", clientes);
        model.addAttribute("pendientes", List.of());
        model.addAttribute("confirmados", List.of());
        model.addAttribute("activos", List.of());

        return "pedidos";
    }

    @GetMapping("/buscar")
    public String buscarPedidos(@RequestParam Long clienteId, Model model) {

        List<Pedido> pedidos = pedidoClient.listarPedidosPorCliente(clienteId.intValue());

        List<Cliente> clientes = usuarioDAO.obtenerClientes();
        model.addAttribute("clientes", clientes);

        model.addAttribute("clienteId", clienteId);
        model.addAttribute("pendientes", pedidos.stream()
                .filter(p -> "SOLICITADO".equalsIgnoreCase(p.getEstadoPedido()))
                .toList());

        model.addAttribute("confirmados", pedidos.stream()
                .filter(p -> "CONFIRMADO".equalsIgnoreCase(p.getEstadoPedido()))
                .toList());

        model.addAttribute("activos", pedidos.stream()
                .filter(p -> "ACTIVO".equalsIgnoreCase(p.getEstadoPedido()))
                .toList());

        return "pedidos";
    }

    @PostMapping("/{id}/confirmar")
    public String confirmarPedido(@PathVariable Integer id, @RequestParam Long clienteId) {
        pedidoClient.confirmarPedido(id);
        return "redirect:/admin/pedidos/buscar?clienteId=" + clienteId;
    }

    @PostMapping("/{id}/denegar")
    public String denegarPedido(@PathVariable Integer id, @RequestParam Long clienteId) {
        pedidoClient.denegarPedido(id);
        return "redirect:/admin/pedidos/buscar?clienteId=" + clienteId;
    }
}


