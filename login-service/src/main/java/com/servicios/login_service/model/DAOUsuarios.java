package com.servicios.login_service.model;

import com.servicios.login_service.model.CRUDUsuarios;
import com.servicios.login_service.model.DTOUsuario;
import com.servicios.login_service.conexion.Conexion;
import java.util.LinkedList;

public class DAOUsuarios extends Conexion implements CRUDUsuarios {

    @Override
    public LinkedList<DTOUsuario> ListarUsuarios() {
        LinkedList<DTOUsuario> lista = new LinkedList<>();
        String sql = "SELECT * FROM usuario";
        try {
            ps = super.con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                DTOUsuario u = new DTOUsuario();
                u.setIdUsuario(rs.getInt("idUsuario"));
                u.setNombre(rs.getString("nombre"));
                u.setAppat(rs.getString("appat"));
                u.setApmat(rs.getString("apmat"));
                u.setDni(rs.getInt("dni"));
                u.setEmail(rs.getString("email"));
                // columna contraseña (hash)
                try {
                    u.setContra(rs.getString("contraseña"));
                } catch (Exception e) {
                    /* ignore */ }
                try {
                    u.setEstado(rs.getInt("estado"));
                } catch (Exception e) {
                    /* ignore */ }
                try {
                    u.setCreador(rs.getInt("creador"));
                } catch (Exception e) {
                    /* ignore */ }
                try {
                    u.setRol(rs.getInt("rol_id"));
                } catch (Exception e) {
                    /* ignore */ }
                lista.add(u);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    /**
     * Valida sesión a partir de correo y contraseña en texto (contra). Devuelve
     * DTOUsuario si coincide (hash), sino null.
     */
    @Override
    public DTOUsuario ValidarSesion(String correo, String contra) {
        DTOUsuario usuario = null;
        try {
            // Reusar getUserByEmail para evitar duplicar selects
            DTOUsuario tmp = getUserByEmail(correo);
            if (tmp != null) {
                // comparar hash con BCrypt (si estás usando BCrypt)
                if (tmp.getContra() != null && org.mindrot.jbcrypt.BCrypt.checkpw(contra, tmp.getContra())) {
                    usuario = tmp;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return usuario;
    }

    // Obtiene usuario por email (incluye campos comunes: rol, creador si existen)
    public DTOUsuario getUserByEmail(String correo) {
        DTOUsuario usuario = null;
        String sql = "SELECT * FROM usuario WHERE email = ? AND estado = 1";
        try {
            ps = super.con.prepareStatement(sql);
            ps.setString(1, correo);
            rs = ps.executeQuery();
            if (rs.next()) {
                usuario = new DTOUsuario();
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setAppat(rs.getString("appat"));
                usuario.setApmat(rs.getString("apmat"));
                usuario.setDni(rs.getInt("dni"));
                usuario.setEmail(rs.getString("email"));
                // contraseña (hash)
                try {
                    usuario.setContra(rs.getString("contraseña"));
                } catch (Exception e) {
                    /* opcional */ }
                try {
                    usuario.setEstado(rs.getInt("estado"));
                } catch (Exception e) {
                    /* opcional */ }
                try {
                    usuario.setCreador(rs.getInt("creador"));
                } catch (Exception e) {
                    /* opcional */ }
                try {
                    usuario.setRol(rs.getInt("rol_id"));
                } catch (Exception e) {
                    /* opcional */ }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return usuario;
    }

    /**
     * Inserta usuario. La tabla puede tener valor por defecto para rol (por
     * ejemplo 3=cliente). Si quieres insertar con rol explícito, crea una
     * sobrecarga distinta.
     */
    public boolean insertarUsuario(DTOUsuario u, int creadorId) {
        // incluimos rol_id en la inserción; escapamos el nombre de la columna contraseña con backticks por seguridad
        String sql = "INSERT INTO usuario(appat, apmat, nombre, dni, email, `contraseña`, creador, rol_id, fechaCreacion, estado) "
                + "VALUES(?,?,?,?,?,?,?,?,NOW(),1)";
        try {
            ps = super.con.prepareStatement(sql);
            ps.setString(1, u.getAppat());
            ps.setString(2, u.getApmat());
            ps.setString(3, u.getNombre());
            ps.setInt(4, u.getDni());
            ps.setString(5, u.getEmail());
            ps.setString(6, u.getContra()); // hashed
            ps.setInt(7, creadorId);
            // si DTO trae rol, úsalo; si no, asigna 3 (cliente) por defecto
            int rolParaInsert = (u.getRol() > 0) ? u.getRol() : 3;
            ps.setInt(8, rolParaInsert);
            int r = ps.executeUpdate();
            return r > 0;
        } catch (Exception ex) {
            ex.printStackTrace(); // mira el stacktrace en la consola para conocer la causa exacta
        }
        return false;
    }

    // Actualiza la contraseña (ya recibida hasheada)
    public boolean actualizarContrasena(String email, String hashedPassword) {
        String sql = "UPDATE usuario SET contraseña = ? WHERE email = ?";
        try {
            ps = super.con.prepareStatement(sql);
            ps.setString(1, hashedPassword);
            ps.setString(2, email);
            int r = ps.executeUpdate();
            return r > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

}
