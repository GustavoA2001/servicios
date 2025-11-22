package com.servicios.admin_service.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.servicios.admin_service.model.*;

@Repository
public class CatalogosDAO {
    private final JdbcTemplate jdbcTemplate;

    public CatalogosDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private String table_distrito() {
        return "Distrito";
    }
    private String table_especialidades() {
        return "Especialidad";
    }
    private String table_roles() {
        return "Rol";
    }

    // ===========
    // DISTRITOS
    // ===========
    public List<Distrito> obtenerDistritos() {
        String sql = "SELECT DistritoID, Nom_Distrito, Estado_Distrito FROM " + this.table_distrito(); 
        
        // Usamos JdbcTemplate para ejecutar la consulta y mapear los resultados
        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Distrito d = new Distrito();
            d.setDistritoID(rs.getInt("DistritoID"));
            d.setNomb_Distrito(rs.getString("Nom_Distrito"));            
            d.setEstado_Distrito(rs.getString("Estado_Distrito"));

            return d;
        });
    }

    // Cambiar estado de un distrito: Habilitar o Inhabilitar
    public void cambiarEstadoDistrito(int id, String estado) {
        String sql = "UPDATE Distrito SET Estado_Distrito = ? WHERE DistritoID = ?";
        jdbcTemplate.update(sql, estado, id);
    }

    public Distrito crearDistrito(Distrito distrito) {
        String sql = "INSERT INTO Distrito (Nom_Distrito, Estado_Distrito) VALUES (?, 'Habilitado')";
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
    
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, distrito.getNomb_Distrito());
            return ps;
        }, keyHolder);
    
        // Asignamos el ID generado al objeto
        distrito.setDistritoID(keyHolder.getKey().intValue());
        distrito.setEstado_Distrito("Habilitado"); // Estado inicial
        return distrito;
    }


    // ===========
    // ESPECIALIDADES
    // ===========
    public List<Especialidad> obtenerEspecialidades() {
        String sql = "SELECT EspecialidadID, Nomb_Especialidad, Descrip_Especialidad, Estado_Especialidad FROM " + this.table_especialidades(); 
        
        // Usamos JdbcTemplate para ejecutar la consulta y mapear los resultados
        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Especialidad e = new Especialidad();
            e.setEspecialidadID(rs.getInt("EspecialidadID"));
            e.setNomb_especialidad(rs.getString("Nomb_Especialidad"));
            e.setDescrip_especialidad(rs.getString("Descrip_Especialidad"));
            e.setEstado_especialidad(rs.getString("Estado_Especialidad"));

            return e;
        });
    }

    public void cambiarEstadoEspecialidad(int id, String estado) {
        String sql = "UPDATE Especialidad SET Estado_Especialidad = ? WHERE EspecialidadID = ?";
        jdbcTemplate.update(sql, estado, id);
    }

    public Especialidad crearEspecialidad(Especialidad especialidad) {
        String sql = "INSERT INTO Especialidad (Nomb_Especialidad, Descrip_Especialidad, Estado_Especialidad) VALUES (?, ?, 'Habilitado')";
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
    
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, especialidad.getNomb_especialidad());
            ps.setString(2, especialidad.getDescrip_especialidad());
            return ps;
        }, keyHolder);
    
        // Asignamos el ID generado al objeto
        especialidad.setEspecialidadID(keyHolder.getKey().intValue());
        especialidad.setEstado_especialidad("Habilitado"); // Estado inicial
        return especialidad;
    }

    public void actualizarEspecialidad(int id, Especialidad especialidad) {
        String sql = "UPDATE Especialidad SET Nomb_Especialidad = ?, Descrip_Especialidad = ? WHERE EspecialidadID = ?";
        jdbcTemplate.update(sql, 
            especialidad.getNomb_especialidad(),
            especialidad.getDescrip_especialidad(),
            id);
    }
    

    // ===========
    // ROLES
    // ===========
    public List<Rol> obtenerRoles() {
        String sql = "SELECT RolID, Nom_Rol, Descrip_Rol, Estado_Rol FROM " + this.table_roles(); 
        
        // Usamos JdbcTemplate para ejecutar la consulta y mapear los resultados
        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Rol r = new Rol();
            r.setRolID(rs.getInt("RolID"));
            r.setNom_rol(rs.getString("Nom_Rol"));
            r.setDescrip_rol(rs.getString("Descrip_Rol"));
            r.setEstado_rol(rs.getString("Estado_Rol"));

            return r;
        });
    }

    public void cambiarEstadoRol(int id, String estado) {
        String sql = "UPDATE Rol SET Estado_Rol = ? WHERE RolID = ?";
        jdbcTemplate.update(sql, estado, id);
    }

    public Rol crearRol(Rol rol) {
        String sql = "INSERT INTO Rol (Nom_Rol, Descrip_Rol, Estado_Rol) VALUES (?, ?, 'Habilitado')";
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
    
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, rol.getNom_rol());
            ps.setString(2, rol.getDescrip_rol());
            return ps;
        }, keyHolder);
    
        // Asignamos el ID generado al objeto
        rol.setRolID(keyHolder.getKey().intValue());
        rol.setEstado_rol("Habilitado"); // Estado inicial
        return rol;
    }

    public void actualizarRol(int id, Rol rol) {
        String sql = "UPDATE Rol SET Nom_Rol = ?, Descrip_Rol = ? WHERE RolID = ?";
        jdbcTemplate.update(sql, 
            rol.getNom_rol(),
            rol.getDescrip_rol(),
            id);
    }
}