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

@WebServlet("/RegistroServlet")
public class RegistroServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Delegar a process (permite extender más tarde)
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirigir GET a página de registro
        response.sendRedirect(request.getContextPath() + "/Vista/registro.jsp");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion");
        if (accion == null || !"registrar".equalsIgnoreCase(accion)) {
            response.sendRedirect(request.getContextPath() + "/Vista/registro.jsp");
            return;
        }

        try {
            String appat = request.getParameter("appat");
            String apmat = request.getParameter("apmat");
            String nombre = request.getParameter("nombre");
            String dniStr = request.getParameter("dni");
            String email = request.getParameter("email");
            String pass = request.getParameter("password");

            if (appat == null || nombre == null || dniStr == null || email == null || pass == null
                    || appat.trim().isEmpty() || nombre.trim().isEmpty() || dniStr.trim().isEmpty()
                    || email.trim().isEmpty() || pass.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/Vista/registro.jsp?error=missing");
                return;
            }

            int dni = 0;
            try {
                dni = Integer.parseInt(dniStr);
            } catch (NumberFormatException nfe) {
                /* deja 0 si no es numérico */ }

            // Evitar duplicados por email
            DAOUsuarios dao = new DAOUsuarios();
            if (dao.getUserByEmail(email) != null) {
                response.sendRedirect(request.getContextPath() + "/Vista/registro.jsp?error=dup");
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
            u.setRol(3); // cliente por defecto (ajusta si necesitas otro)

            int creadorId = 1; // admin por defecto; ajusta según tu lógica
            boolean ok = dao.insertarUsuario(u, creadorId);

            if (ok) {
                response.sendRedirect(request.getContextPath() + "/Vista/login.jsp?registro=ok");
            } else {
                response.sendRedirect(request.getContextPath() + "/Vista/registro.jsp?error=bd");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/Vista/registro.jsp?error=ex");
        }
    }
}
