package com.servicios.admin_service.dao;

import com.servicios.admin_service.model.Servicio;
import com.servicios.admin_service.model.Pedido;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ServicioDAO {
    private final JdbcTemplate jdbcTemplate;

    public ServicioDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private String table_servicio() {    return "Servicio";    }

    /*
     * ============
     * OBTENEMOS LOS SERVICIOS QUE SE MUESTRAN EN LA LISTA
     * =============
     */
    public List<Servicio> obtenerServicios() {
        String sql = "SELECT " +
                "s.ServicioID, s.Nomb_Servicio, s.Descrip_Servicio, s.Precio_Servicio, " +
                "c.CategoriaServicioID, c.Nomb_Categoria, " +
                "e.EmpleadoID, e.Nomb_Empleado, e.Apel_Empleado " +
                "FROM " + this.table_servicio() + " s " +
                "INNER JOIN CategoriaServicio c ON s.CategoriaServicioID = c.CategoriaServicioID " +
                "INNER JOIN EmpleadoEspecialidad ee ON s.ServicioID = ee.ServicioID " +
                "INNER JOIN Empleado e ON ee.EmpleadoID = e.EmpleadoID";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Servicio s = new Servicio();
            s.setServicioId(rs.getInt("ServicioID"));
            s.setNomb_Servicio(rs.getString("Nomb_Servicio"));
            s.setDescrip_Servicio(rs.getString("Descrip_Servicio"));
            s.setPrecio_Servicio(rs.getDouble("Precio_Servicio"));

            s.getCategoria().setCategoriaServicioID(rs.getInt("CategoriaServicioID"));
            s.getCategoria().setNombreCategoria(rs.getString("Nomb_Categoria"));

            s.getEmpleado().setId(rs.getLong("EmpleadoID"));
            s.getEmpleado().setNombre(rs.getString("Nomb_Empleado"));
            s.getEmpleado().setApellido(rs.getString("Apel_Empleado"));

            return s;
        });
    }

    /*
     * =============
     * OBTENEMOS LOS CLIENTES QUE SOLICITARON EL SERVICIO
     * ==============
     */
    public List<Pedido> obtenerClientesServicio(int servicioId) {
        String sql = """
                    SELECT
                        p.PedidoID, p.FechaPedido, p.EstadoPedido, p.DuracionPedido, p.CostoTotal,
                        c.ClienteID, c.Nom_Cliente, c.Apel_Cliente
                    FROM Pedido p
                    INNER JOIN Cliente c ON p.ClienteID = c.ClienteID
                    INNER JOIN Servicio s ON p.ServicioID = s.ServicioID
                    WHERE s.ServicioID = ?
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Pedido p = new Pedido();
            p.setPedidoID(rs.getInt("PedidoID"));
            p.setFechaPedido(rs.getObject("FechaPedido", java.time.LocalDateTime.class));
            p.setEstadoPedido(rs.getString("EstadoPedido"));
            p.setDuracionPedido(rs.getInt("DuracionPedido"));
            p.setCostoTotal(rs.getDouble("CostoTotal"));

            // Cliente
            p.getCliente().setId(rs.getLong("ClienteID"));
            p.getCliente().setNombre(rs.getString("Nom_Cliente"));
            p.getCliente().setApellido(rs.getString("Apel_Cliente"));

            return p;

        }, servicioId);
    }

    @SuppressWarnings("deprecation")
    public List<Servicio> buscarServicios(String search) {
        // Escapamos el término de búsqueda para prevenir problemas con caracteres
        // especiales
        String searchTerm = "%" + search + "%"; // El % es para hacer búsqueda parcial

        // Consulta SQL con LIKE en nombre del servicio, precio, categoría y empleado
        String sql = """
                            SELECT
                                s.ServicioID, s.Nomb_Servicio, s.Descrip_Servicio, s.Precio_Servicio,
                                c.CategoriaServicioID, c.Nomb_Categoria,
                                e.EmpleadoID, e.Nomb_Empleado, e.Apel_Empleado
                            FROM Servicio s
                            INNER JOIN CategoriaServicio c ON s.CategoriaServicioID = c.CategoriaServicioID
                            INNER JOIN EmpleadoEspecialidad ee ON s.ServicioID = ee.ServicioID
                            INNER JOIN Empleado e ON ee.EmpleadoID = e.EmpleadoID
                WHERE CAST(s.ServicioID AS CHAR) LIKE ?
                   OR s.Nomb_Servicio LIKE ?
                   OR s.Descrip_Servicio LIKE ?
                   OR c.Nomb_Categoria LIKE ?
                   OR CAST(s.Precio_Servicio AS CHAR) LIKE ?
                   OR e.Nomb_Empleado LIKE ?
                   OR e.Apel_Empleado LIKE ?

                        """;

        return jdbcTemplate.query(sql, new Object[] {
                searchTerm, // ServicioID
                searchTerm, // Nomb_Servicio
                searchTerm, // Descrip_Servicio
                searchTerm, // Nomb_Categoria
                searchTerm, // Precio_Servicio
                searchTerm, // Nomb_Empleado
                searchTerm // Apel_Empleado
        }, (rs, rowNum) -> {
            Servicio s = new Servicio();
            s.setServicioId(rs.getInt("ServicioID"));
            s.setNomb_Servicio(rs.getString("Nomb_Servicio"));
            s.setDescrip_Servicio(rs.getString("Descrip_Servicio"));
            s.setPrecio_Servicio(rs.getDouble("Precio_Servicio"));

            s.getCategoria().setCategoriaServicioID(rs.getInt("CategoriaServicioID"));
            s.getCategoria().setNombreCategoria(rs.getString("Nomb_Categoria"));

            s.getEmpleado().setId(rs.getLong("EmpleadoID"));
            s.getEmpleado().setNombre(rs.getString("Nomb_Empleado"));
            s.getEmpleado().setApellido(rs.getString("Apel_Empleado"));

            return s;
        });
    }

    // === ACTUALIZAR ESTADO SERVICIO ===
    public void actualizarEstadoServicio(int servicioId, String nuevoEstado) {
        String sql = "UPDATE servicio SET EstadoServicio = ? WHERE ServicioID = ?";
        jdbcTemplate.update(sql, nuevoEstado, servicioId);
    }

}
