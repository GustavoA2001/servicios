package com.servicios.pagos_service.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.servicios.pagos_service.model.InformePagoDTO;
import com.servicios.pagos_service.model.Pago;

@Repository
public class PagoDAO {

    private final JdbcTemplate jdbcTemplate;

    public PagoDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertarPago(Integer pedidoId, Integer clienteId, String metodo,
            BigDecimal monto, String moneda, String estado, String paypalOrderId) {

        System.out.println("[DAO] Insertando pago pedidoId=" + pedidoId);

        jdbcTemplate.update("""
                INSERT INTO pago (PedidoID, ClienteID, MetodoPago, Monto, Moneda, EstadoPago, PaypalOrderID)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """, pedidoId, clienteId, metodo, monto, moneda, estado, paypalOrderId);
    }

    public void actualizarPago(String paypalOrderId, String estado, String captureId, String detalles) {

        System.out.println("[DAO] Actualizando pago PayPalOrderID=" + paypalOrderId);

        jdbcTemplate.update("""
                    UPDATE pago SET EstadoPago=?, PaypalCaptureID=?, FechaPago=NOW(), Detalles=?
                    WHERE PaypalOrderID=?
                """, estado, captureId, detalles, paypalOrderId);
    }

    public void insertarTransaccion(String paypalOrderId, String tipo, String estado, String detallesJson) {
        System.out.println("[DAO] Insertando transacción PayPalOrderID=" + paypalOrderId);

        String sql = "INSERT INTO transaccion (PagoID, Tipo, Estado, Detalles) " +
                "VALUES ((SELECT PagoID FROM pago WHERE PaypalOrderID=?), ?, ?, ?)";

        jdbcTemplate.update(sql, paypalOrderId, tipo, estado, detallesJson);
    }

    // =====
    // CLIENT DESDE EL ADMIN-SERVICE
    // =====
    public List<Pago> findAll() {
        String sql = "SELECT * FROM pago"; 
        return jdbcTemplate.query(sql, new PagoRowMapper());
    }

    public List<Pago> findByPedidoId(Integer pedidoId) {
        String sql = "SELECT * FROM pago WHERE PedidoID = ?";
        return jdbcTemplate.query(sql, new PagoRowMapper(), pedidoId);
    }

    public Optional<Pago> findById(Long pagoId) {
        String sql = "SELECT * FROM pago WHERE PagoID = ?";
        List<Pago> result = jdbcTemplate.query(sql, new PagoRowMapper(), pagoId);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    
    public List<InformePagoDTO> findAllInformes() {
        String sql = """
            SELECT 
                p.PagoID,
                p.PedidoID,
                p.ClienteID,
                p.MetodoPago,
                p.Monto,
                p.Moneda,
                p.EstadoPago,
                p.PaypalOrderID,
                p.PaypalCaptureID,
                p.TipoTarjeta,
                p.UltimosDigitos,
                p.FechaPago,
                p.Detalles,
                CONCAT(c.Nom_Cliente, ' ', c.Apel_Cliente) AS NombreCliente,
                s.nomb_servicio AS ServicioContratado,
                e.EmpleadoID,
                CONCAT(e.nomb_empleado, ' ', e.apel_empleado) AS NombreEmpleado
            FROM pago p
            JOIN pedido pe ON p.PedidoID = pe.PedidoID
            JOIN cliente c ON p.ClienteID = c.ClienteID
            JOIN servicio s ON pe.ServicioID = s.ServicioID
            LEFT JOIN empleadoespecialidad ee ON s.ServicioID = ee.ServicioID
            LEFT JOIN empleado e ON ee.EmpleadoID = e.EmpleadoID
        """;
    
        return jdbcTemplate.query(sql, new InformePagoRowMapper());
    }

}
