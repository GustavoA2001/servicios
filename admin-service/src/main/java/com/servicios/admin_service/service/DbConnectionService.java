package com.servicios.admin_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class DbConnectionService {

    @Autowired
    private DataSource dataSource;

    public String testConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            if (connection != null) {
                return "Conexión exitosa a la base de datos!";
            } else {
                return "No se pudo establecer conexión con la base de datos.";
            }
        } catch (SQLException e) {
            return "Error al conectar a la base de datos: " + e.getMessage();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    return "Error al cerrar la conexión: " + e.getMessage();
                }
            }
        }
    }
}
