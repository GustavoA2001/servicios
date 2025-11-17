/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.servicios.registro_service.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.servicios.registro_service.model.DAOUsuarios;
import com.servicios.registro_service.model.DTOUsuario;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Administrador
 */
@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion");
        try {
            if ("registrar".equals(accion)) {
                registrar(request, response);
            } else {
                response.sendRedirect("Vista/registro.jsp");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendRedirect("Vista/registro.jsp?error=ex");
        }
    }

    private void registrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String appat = request.getParameter("appat");
            String apmat = request.getParameter("apmat");
            String nombre = request.getParameter("nombre");
            String dniStr = request.getParameter("dni");
            String email = request.getParameter("email");
            String pass = request.getParameter("password");

            if (appat == null || nombre == null || dniStr == null || email == null || pass == null) {
                response.sendRedirect("Vista/registro.jsp?error=missing");
                return;
            }

            int dni = 0;
            try { dni = Integer.parseInt(dniStr); } catch (NumberFormatException nfe) { /* deja 0 */ }

            DAOUsuarios dao = new DAOUsuarios();
            // evitar duplicados por email
            if (dao.getUserByEmail(email) != null) {
                response.sendRedirect("Vista/registro.jsp?error=dup");
                return;
            }

            String hashed = BCrypt.hashpw(pass, BCrypt.gensalt(12));

            DTOUsuario u = new DTOUsuario();
            u.setAppat(appat);
            u.setApmat(apmat);
            u.setNombre(nombre);
            u.setDni(dni);
            u.setEmail(email);
            u.setContra(hashed);
            u.setRol(3); // cliente por defecto

            int creadorId = 1; // admin por defecto (ajusta si hace falta)
            boolean ok = dao.insertarUsuario(u, creadorId);

            if (ok) response.sendRedirect("Vista/login.jsp?registro=ok");
            else response.sendRedirect("Vista/registro.jsp?error=bd");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendRedirect("Vista/registro.jsp?error=ex");
        }
    }

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { processRequest(request, response); }
    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { processRequest(request, response); }
}