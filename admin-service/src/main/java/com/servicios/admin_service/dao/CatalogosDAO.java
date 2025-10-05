package com.servicios.admin_service.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
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
}