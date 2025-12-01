package com.servicios.login_service.dao;

import com.servicios.login_service.model.Usuario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UsuarioDAO {
    private final JdbcTemplate jdbc;

    public UsuarioDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Optional<Usuario> findClienteByEmail(String email) {
        System.out.println("DAO: Buscando cliente por email " + email);

        String sql = "SELECT ClienteID, Email_Cliente, Password_Cliente, 'cliente' as rol FROM cliente WHERE Email_Cliente = ?";

        try {
            Usuario u = jdbc.queryForObject(sql, (rs, rowNum) -> {
                Usuario user = new Usuario();
                user.setId(rs.getLong("ClienteID"));
                user.setEmail(rs.getString("Email_Cliente"));
                user.setPassword(rs.getString("Password_Cliente"));
                user.setRol("CLIENTE");

                System.out.println("DAO: Cliente encontrado -> rol CLIENTE");

                return user;
            }, email);

            return Optional.of(u);

        } catch (Exception e) {
            System.out.println("DAO: Cliente NO encontrado");

            return Optional.empty();
        }
    }

    
    public Optional<Usuario> findEmpleadoByEmail(String email) {
        System.out.println("DAO: Buscando empleado por email " + email);

        String sql = """
                    SELECT e.EmpleadoID, e.Email_Empleado, e.Password_Empleado, r.Nom_Rol
                    FROM empleado e
                    JOIN rol r ON e.RolID = r.RolID
                    WHERE e.Email_Empleado = ?
                """;

        try {
            Usuario u = jdbc.queryForObject(sql, (rs, rowNum) -> {
                Usuario user = new Usuario();
                user.setId(rs.getLong("EmpleadoID"));
                user.setEmail(rs.getString("Email_Empleado"));
                user.setPassword(rs.getString("Password_Empleado"));
                user.setRol(rs.getString("Nom_Rol"));

                System.out.println("DAO: Empleado encontrado -> rol " + rs.getString("Nom_Rol"));

                return user;
            }, email);

            return Optional.of(u);

        } catch (Exception e) {
            System.out.println("DAO: Empleado NO encontrado");

            return Optional.empty();
        }
    }

}