package com.servicios.registro_service.conexion;
import java.sql.*;
public class Conexion implements Parametros {
    public Connection con;
    public PreparedStatement ps;
    public Statement smt;
    public ResultSet rs;
    public String mensaje;
    public Conexion(){
        try{
            Class.forName(DRIVER);
            con =  DriverManager.getConnection(URL,USER,CLAVE);
            smt = con.createStatement();
            mensaje="Conexión Establecida";
        }catch(Exception ex){
            mensaje="ERROR al conectar a la base de datos.."+ex;
        }
    }
    public Connection getConnection(){
        return con;
    } 
    
}