package com.servicios.admin_service.dao;

import com.servicios.admin_service.model.Cliente;
import com.servicios.admin_service.model.Empleado;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class UsuarioDAO {

    private final JdbcTemplate jdbcTemplate;

    private String table_cliente() {
        return "cliente";
    }

    private String table_empleado() {
        return "empleado";
    }

    public UsuarioDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Clientes
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

    // Empleado (trabajador + administrador)
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


    public Cliente obtenerClientePorId(Long id) {
        String sql = "SELECT ClienteID, Nom_Cliente, Apel_Cliente, Email_Cliente, Password_Cliente, dni, FotoCliente, EstadoCliente, FechaRegistro "
                +
                "FROM "+ this.table_cliente() +" WHERE ClienteID = ?";
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

    public Empleado obtenerEmpleadoPorId(Long id) {
        String sql = "SELECT EmpleadoID, Nomb_Empleado, Apel_Empleado, Email_Empleado, Password_Empleado, dni, telefono, EstadoEmpleado, FotoEmpleado, rolID, FechaRegistro "
                +
                "FROM "+ this.table_empleado() +" WHERE EmpleadoID = ?";
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

}
