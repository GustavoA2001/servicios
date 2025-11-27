package com.servicios.admin_service.dao;

import com.servicios.admin_service.model.Cliente;
import com.servicios.admin_service.model.Empleado;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioDAO {

    private final JdbcTemplate jdbcTemplate;

    public UsuarioDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private String table_cliente() {
        return "cliente";
    }

    private String table_empleado() {
        return "empleado";
    }

    // =======================
    // CLIENTES
    // =======================

    public List<Cliente> obtenerClientes() {
        String sql = "SELECT ClienteID, Nom_Cliente, Apel_Cliente, Email_Cliente, Password_Cliente, dni, FotoCliente, EstadoCliente, FechaRegistro "
                +
                "FROM " + this.table_cliente() + ";";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Cliente c = new Cliente();
            c.setId(rs.getLong("ClienteID"));
            c.setNombre(rs.getString("Nom_Cliente"));
            c.setApellido(rs.getString("Apel_Cliente"));
            c.setEmail(rs.getString("Email_Cliente"));
            c.setPwd(rs.getString("Password_Cliente"));
            c.setDNI(rs.getString("dni"));
            c.setFotito(rs.getString("FotoCliente"));
            c.setEstado(rs.getString("EstadoCliente"));
            c.setFechaRegistro(rs.getObject("FechaRegistro", LocalDateTime.class));
            return c;
        });

    }

    public Cliente obtenerClientePorId(Long id) {
        String sql = "SELECT ClienteID, Nom_Cliente, Apel_Cliente, Email_Cliente, Password_Cliente, dni, FotoCliente, EstadoCliente, FechaRegistro "
                +
                "FROM " + this.table_cliente() + " WHERE ClienteID = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Cliente c = new Cliente();
            c.setId(rs.getLong("ClienteID"));
            c.setNombre(rs.getString("Nom_Cliente"));
            c.setApellido(rs.getString("Apel_Cliente"));
            c.setEmail(rs.getString("Email_Cliente"));
            c.setPwd(rs.getString("Password_Cliente"));
            c.setDNI(rs.getString("dni"));
            c.setFotito(rs.getString("FotoCliente"));
            c.setEstado(rs.getString("EstadoCliente"));
            c.setFechaRegistro(rs.getObject("FechaRegistro", LocalDateTime.class));
            return c;
        }, id);
    }

    // ====== INSERTAMOS UN NUEVO CLIENTE
    public int insertarCliente(Cliente cliente) {
        String sql = "INSERT INTO cliente (Nom_Cliente, Apel_Cliente, Email_Cliente, Password_Cliente, EstadoCliente, FechaRegistro) "
                +
                "VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getEmail(),
                cliente.getPwd(),
                cliente.getEstado(),
                cliente.getFechaRegistro());
    }

    public int actualizarCliente(Long id, Cliente c) {
        StringBuilder sql = new StringBuilder("UPDATE cliente SET ");
        List<Object> params = new ArrayList<>();

        if (c.getNombre() != null) {
            sql.append("Nom_Cliente = ?, ");
            params.add(c.getNombre());
        }
        if (c.getApellido() != null) {
            sql.append("Apel_Cliente = ?, ");
            params.add(c.getApellido());
        }
        if (c.getEmail() != null) {
            sql.append("Email_Cliente = ?, ");
            params.add(c.getEmail());
        }
        if (c.getDNI() != null) {
            sql.append("DNI = ?, ");
            params.add(c.getDNI());
        }
        if (c.getFotito() != null) {
            sql.append("FotoCliente = ?, ");
            params.add(c.getFotito());
        }
        if (c.getPwd() != null) {
            sql.append("Password_Cliente = ?, ");
            params.add(c.getPwd());
        }
        if (c.getEstado() != null) {
            sql.append("EstadoCliente = ?, ");
            params.add(c.getEstado());
        }

        // Elimina la última coma y espacio
        if (params.isEmpty())
            return 0;
        sql.setLength(sql.length() - 2);

        sql.append(" WHERE ClienteID = ?");
        params.add(id);

        return jdbcTemplate.update(sql.toString(), params.toArray());
    }

    public int cambiarEstadoCliente(Long id, String nuevoEstado) {
        String sql = "UPDATE cliente SET EstadoCliente = ? WHERE ClienteID = ?";
        return jdbcTemplate.update(sql, nuevoEstado, id);
    }

    public int eliminarCliente(Long id) {
        String sql = "DELETE FROM cliente WHERE ClienteID = ?";
        return jdbcTemplate.update(sql, id);
    }

    // =======================
    // EMPLEADOS
    // =======================

    // ===== OBTENEMOS LOS DATOS DEL EMPLEADO (TRABAJADOR , ADMINISTRADOR) USAMOS LA
    // FK ROLID
    public List<Empleado> obtenerEmpleadosPorRol(int rolID) {
        String sql = "SELECT EmpleadoID, Nomb_Empleado, Apel_Empleado, Email_Empleado, Password_Empleado, dni, telefono, EstadoEmpleado, FotoEmpleado, rolID, FechaRegistro "
                +
                "FROM empleado WHERE rolID = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Empleado e = new Empleado();
            e.setId(rs.getLong("EmpleadoID"));
            e.setNombre(rs.getString("Nomb_Empleado"));
            e.setApellido(rs.getString("Apel_Empleado"));
            e.setEmail(rs.getString("Email_Empleado"));
            e.setPwd(rs.getString("Password_Empleado"));
            e.setDNI(rs.getString("dni"));
            e.setTelefono(rs.getString("telefono"));
            e.setEstado(rs.getString("EstadoEmpleado"));
            e.setFotito(rs.getString("FotoEmpleado"));
            e.setRolID(rs.getInt("rolID"));
            e.setFechaRegistro(rs.getObject("FechaRegistro", LocalDateTime.class));
            return e;
        }, rolID);
    }

    // ===== RECOGEMOS LOS DATOS DEL EMPLEADO POR SU ID
    public Empleado obtenerEmpleadoPorId(Long id) {
        String sql = "SELECT EmpleadoID, Nomb_Empleado, Apel_Empleado, Email_Empleado, Password_Empleado, dni, telefono, EstadoEmpleado, FotoEmpleado, rolID, FechaRegistro "
                +
                "FROM " + this.table_empleado() + " WHERE EmpleadoID = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Empleado e = new Empleado();
            e.setId(rs.getLong("EmpleadoID"));
            e.setNombre(rs.getString("Nomb_Empleado"));
            e.setApellido(rs.getString("Apel_Empleado"));
            e.setEmail(rs.getString("Email_Empleado"));
            e.setPwd(rs.getString("Password_Empleado"));
            e.setDNI(rs.getString("dni"));
            e.setTelefono(rs.getString("telefono"));
            e.setEstado(rs.getString("EstadoEmpleado"));
            e.setFotito(rs.getString("FotoEmpleado"));
            e.setRolID(rs.getInt("rolID"));
            e.setFechaRegistro(rs.getObject("FechaRegistro", LocalDateTime.class));
            return e;
        }, id);
    }

    // INSERTAMOS UN NUEVO EMPLEADO
    public int insertarEmpleado(Empleado empleado) {
        String sql = "INSERT INTO empleado (Nomb_Empleado, Apel_Empleado, Email_Empleado, Password_Empleado, EstadoEmpleado, FechaRegistro, RolID) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                empleado.getNombre(),
                empleado.getApellido(),
                empleado.getEmail(),
                empleado.getPwd(),
                empleado.getEstado(),
                empleado.getFechaRegistro(),
                empleado.getRolID());
    }

    public int actualizarEmpleado(Long id, Empleado e) {
        StringBuilder sql = new StringBuilder("UPDATE empleado SET ");
        List<Object> params = new ArrayList<>();

        if (e.getNombre() != null) {
            sql.append("Nomb_Empleado = ?, ");
            params.add(e.getNombre());
        }
        if (e.getApellido() != null) {
            sql.append("Apel_Empleado = ?, ");
            params.add(e.getApellido());
        }
        if (e.getEmail() != null) {
            sql.append("Email_Empleado = ?, ");
            params.add(e.getEmail());
        }
        if (e.getDNI() != null) {
            sql.append("DNI = ?, ");
            params.add(e.getDNI());
        }
        if (e.getTelefono() != null) {
            sql.append("Telefono = ?, ");
            params.add(e.getTelefono());
        }
        if (e.getEstado() != null) {
            sql.append("EstadoEmpleado = ?, ");
            params.add(e.getEstado());
        }
        if (e.getRolID() != 0) { // 0 = sin cambio
            sql.append("RolID = ?, ");
            params.add(e.getRolID());
        }
        if (e.getFotito() != null) {
            sql.append("FotoEmpleado = ?, ");
            params.add(e.getFotito());
        }
        if (e.getPwd() != null) {
            sql.append("Password_Empleado = ?, ");
            params.add(e.getPwd());
        }
        if (e.getUbicacionPartida() != null) {
            sql.append("UbicacionPartida = ?, ");
            params.add(e.getUbicacionPartida());
        }

        if (params.isEmpty())
            return 0;
        sql.setLength(sql.length() - 2);

        sql.append(" WHERE EmpleadoID = ?");
        params.add(id);

        return jdbcTemplate.update(sql.toString(), params.toArray());
    }

    public int cambiarEstadoEmpleado(Long id, String nuevoEstado) {
        String sql = "UPDATE empleado SET EstadoEmpleado = ? WHERE EmpleadoID = ?";
        return jdbcTemplate.update(sql, nuevoEstado, id);
    }

    public int eliminarEmpleado(Long id) {
        String sql = "DELETE FROM empleado WHERE EmpleadoID = ?";
        return jdbcTemplate.update(sql, id);
    }
}
