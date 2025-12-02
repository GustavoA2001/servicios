package com.servicios.registro_service.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioDAO {

    private final JdbcTemplate jdbc;

    public UsuarioDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void insertCliente(String nombre, String apellido, String email, String passwordHash, String dni) {
        System.out.println("DAO: Ejecutando INSERT CLIENTE...");
        System.out.println("nombre: " + nombre + ", apellido: " + apellido + ", email: " + email);

        String sql = "INSERT INTO cliente (Nom_Cliente, Apel_Cliente, Email_Cliente, Password_Cliente, DNI, FechaRegistro, EstadoCliente) VALUES (?,?,?,?,?,NOW(),'Activo')";

        int filas = jdbc.update(sql, nombre, apellido, email, passwordHash, dni);

        System.out.println("DAO: Filas afectadas: " + filas);
    }

    public void insertEmpleado(String nombre, String apellido, String email, String passwordHash, String dni,
            int rolId) {
        System.out.println("DAO: Ejecutando INSERT EMPLEADO...");
        System.out.println("email: " + email + ", rolId: " + rolId);

        String sql = "INSERT INTO empleado (Nomb_Empleado, Apel_Empleado, Email_Empleado, Password_Empleado, DNI, FechaRegistro, EstadoEmpleado, RolID) VALUES (?,?,?,?,?,NOW(),'Activo',?)";

        int filas = jdbc.update(sql, nombre, apellido, email, passwordHash, dni, rolId);

        System.out.println("DAO: Filas afectadas: " + filas);
    }
}