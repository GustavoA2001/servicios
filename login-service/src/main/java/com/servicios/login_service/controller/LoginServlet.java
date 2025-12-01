package com.servicios.login_service.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.servicios.login_service.model.DAOUsuarios;
import com.servicios.login_service.model.DTOUsuario;
import com.servicios.login_service.util.EmailUtil;
import javax.mail.MessagingException;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Tiempo de expiración (milisegundos)
    private static final long EXPIRE_2FA_MS = 5 * 60 * 1000L;   // 5 minutos
    private static final long EXPIRE_RECUP_MS = 10 * 60 * 1000L; // 10 minutos

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forzar UTF-8
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion");
        try {
            if (accion != null) {
                switch (accion) {
                    case "verificar":
                        verificarLogin(request, response);
                        break;
                    case "confirmarCodigo":
                        confirmarCodigo(request, response);
                        break;
                    case "reenviarCodigo":
                        reenviarCodigo(request, response);
                        break;
                    case "cerrar":
                        cerrarSesion(request, response);
                        break;
                    case "solicitarRecuperacion":
                        solicitarRecuperacion(request, response);
                        break;
                    case "verificarRecuperacion":
                        verificarRecuperacion(request, response);
                        break;
                    case "cambiarPassword":
                        cambiarPassword(request, response);
                        break;
                    default:
                        response.sendRedirect(request.getContextPath() + "/Vista/login.jsp");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/Vista/login.jsp");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/Vista/login.jsp?error=ex");
        }
    }

    // -----------------------
    // Login / 2FA methods
    // -----------------------
    private void verificarLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String correo = request.getParameter("txtCorreo");
            String contra = request.getParameter("txtPassword");

            if (correo == null || correo.trim().isEmpty() || contra == null || contra.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/Vista/login.jsp?error=cred");
                return;
            }

            DAOUsuarios dao = new DAOUsuarios();
            DTOUsuario user = dao.getUserByEmail(correo);

            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/Vista/login.jsp?error=cred");
                return;
            }

            // comprobar hash con BCrypt
            if (user.getContra() != null && BCrypt.checkpw(contra, user.getContra())) {
                String codigo = String.format("%06d", (int) (Math.random() * 900000) + 100000);
                HttpSession ses = request.getSession(true);
                ses.setAttribute("codigo2FA", codigo);
                ses.setAttribute("userTemp", user);
                long expiracion = System.currentTimeMillis() + EXPIRE_2FA_MS;
                ses.setAttribute("codigoExpira", expiracion);

                try {
                    EmailUtil.enviarCodigoVerificacion(user.getEmail(), codigo);
                } catch (MessagingException me) {
                    me.printStackTrace();
                    // limpiar datos temporales por seguridad
                    ses.removeAttribute("codigo2FA");
                    ses.removeAttribute("codigoExpira");
                    ses.removeAttribute("userTemp");
                    response.sendRedirect(request.getContextPath() + "/Vista/login.jsp?error=mail");
                    return;
                }

                // Redirigir a la página de verificación
                response.sendRedirect(request.getContextPath() + "/Vista/verificarCodigo.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/Vista/login.jsp?error=cred");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/Vista/login.jsp?error=ex");
        }
    }

    private void reenviarCodigo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession ses = request.getSession(false);
            if (ses == null) {
                response.sendRedirect(request.getContextPath() + "/Vista/login.jsp?error=sess");
                return;
            }
            DTOUsuario userTemp = (DTOUsuario) ses.getAttribute("userTemp");
            if (userTemp == null) {
                response.sendRedirect(request.getContextPath() + "/Vista/login.jsp?error=sess");
                return;
            }

            String codigo = String.format("%06d", (int) (Math.random() * 900000) + 100000);
            ses.setAttribute("codigo2FA", codigo);
            long expiracion = System.currentTimeMillis() + EXPIRE_2FA_MS;
            ses.setAttribute("codigoExpira", expiracion);

            try {
                EmailUtil.enviarCodigoVerificacion(userTemp.getEmail(), codigo);
            } catch (MessagingException me) {
                me.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/Vista/verificarCodigo.jsp?error=mail");
                return;
            }

            response.sendRedirect(request.getContextPath() + "/Vista/verificarCodigo.jsp");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/Vista/login.jsp?error=ex");
        }
    }

    private void confirmarCodigo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String codigoEnviado = request.getParameter("txtCodigo");
            HttpSession ses = request.getSession(false);
            if (ses == null) {
                response.sendRedirect(request.getContextPath() + "/Vista/login.jsp");
                return;
            }

            Object oCodigo = ses.getAttribute("codigo2FA");
            Object oExpira = ses.getAttribute("codigoExpira");
            String codigoCorrecto = oCodigo != null ? oCodigo.toString() : null;
            long expira = parseLongSafely(oExpira);

            if (codigoCorrecto != null && expira > 0 && System.currentTimeMillis() <= expira && codigoCorrecto.equals(codigoEnviado)) {
                DTOUsuario user = (DTOUsuario) ses.getAttribute("userTemp");
                if (user == null) {
                    response.sendRedirect(request.getContextPath() + "/Vista/login.jsp");
                    return;
                }

                // Guardar usuario real en sesión
                ses.setAttribute("user", user);
                ses.setMaxInactiveInterval(30 * 60); // 30 minutos

                // limpiar 2FA temporales
                ses.removeAttribute("codigo2FA");
                ses.removeAttribute("codigoExpira");
                ses.removeAttribute("userTemp");

                // redirigir según rol
                int rol = user.getRol();
                String ctx = request.getContextPath();
                if (rol == 1) {
                    response.sendRedirect(ctx + "/Vista/index_admin.jsp");
                } else if (rol == 2) {
                    response.sendRedirect(ctx + "/Vista/index_empleado.jsp");
                } else if (rol == 3) {
                    response.sendRedirect(ctx + "/Vista/index.jsp");
                } else {
                    response.sendRedirect(ctx + "/Vista/login.jsp?error=rol");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/Vista/verificarCodigo.jsp?error=cod");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/Vista/login.jsp?error=ex2");
        }
    }

    private long parseLongSafely(Object o) {
        if (o == null) {
            return 0L;
        }
        if (o instanceof Long) {
            return (Long) o;
        }
        if (o instanceof Integer) {
            return ((Integer) o).longValue();
        }
        if (o instanceof String) {
            try {
                return Long.parseLong((String) o);
            } catch (Exception e) {
                return 0L;
            }
        }
        return 0L;
    }

    private void cerrarSesion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            HttpSession ses = request.getSession(false);
            if (ses != null) {
                ses.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/Vista/login.jsp");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/Vista/login.jsp?error=ex");
        }
    }

    // -----------------------
    // Recuperación de contraseña
    // -----------------------
    private void solicitarRecuperacion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String correo = request.getParameter("txtCorreoRecup");
            if (correo == null || correo.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/Vista/recuperar.jsp?error=missing");
                return;
            }

            DAOUsuarios dao = new DAOUsuarios();
            DTOUsuario user = dao.getUserByEmail(correo);
            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/Vista/recuperar.jsp?error=noexist");
                return;
            }

            String codigo = String.format("%06d", (int) (Math.random() * 900000) + 100000);
            HttpSession ses = request.getSession(true);
            ses.setAttribute("recupEmail", correo);
            ses.setAttribute("recupCodigo", codigo);
            ses.setAttribute("recupExpira", System.currentTimeMillis() + EXPIRE_RECUP_MS);
            ses.setAttribute("recupIntentos", 0);

            try {
                EmailUtil.enviarCodigoVerificacion(correo, codigo);
            } catch (MessagingException me) {
                me.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/Vista/recuperar.jsp?error=mail");
                return;
            }

            response.sendRedirect(request.getContextPath() + "/Vista/recuperarCodigo.jsp");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/Vista/recuperar.jsp?error=ex");
        }
    }

    private void verificarRecuperacion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String codigoIngresado = request.getParameter("txtCodigoRecup");
            HttpSession ses = request.getSession(false);
            if (ses == null) {
                response.sendRedirect(request.getContextPath() + "/Vista/recuperar.jsp");
                return;
            }

            Object oCodigo = ses.getAttribute("recupCodigo");
            Object oExpira = ses.getAttribute("recupExpira");
            Object oIntentos = ses.getAttribute("recupIntentos");

            String codigoCorrecto = oCodigo != null ? oCodigo.toString() : null;
            long expira = parseLongSafely(oExpira);

            int intentos = 0;
            if (oIntentos instanceof Integer) {
                intentos = (Integer) oIntentos;
            } else if (oIntentos instanceof Long) {
                intentos = ((Long) oIntentos).intValue();
            }

            if (expira > 0 && System.currentTimeMillis() > expira) {
                ses.removeAttribute("recupCodigo");
                ses.removeAttribute("recupExpira");
                ses.removeAttribute("recupIntentos");
                response.sendRedirect(request.getContextPath() + "/Vista/recuperar.jsp?error=exp");
                return;
            }

            if (codigoCorrecto != null && codigoCorrecto.equals(codigoIngresado)) {
                // permitir cambiar password
                response.sendRedirect(request.getContextPath() + "/Vista/cambiarPassword.jsp");
            } else {
                intentos++;
                ses.setAttribute("recupIntentos", intentos);
                if (intentos >= 3) {
                    ses.removeAttribute("recupCodigo");
                    ses.removeAttribute("recupExpira");
                    ses.removeAttribute("recupIntentos");
                    response.sendRedirect(request.getContextPath() + "/Vista/recuperar.jsp?error=blocked");
                } else {
                    response.sendRedirect(request.getContextPath() + "/Vista/recuperarCodigo.jsp?error=cod");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/Vista/recuperar.jsp?error=ex");
        }
    }

    private void cambiarPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String pass1 = request.getParameter("password1");
            String pass2 = request.getParameter("password2");

            if (pass1 == null || pass2 == null || !pass1.equals(pass2)) {
                response.sendRedirect(request.getContextPath() + "/Vista/cambiarPassword.jsp?error=match");
                return;
            }
            if (pass1.length() < 6) {
                response.sendRedirect(request.getContextPath() + "/Vista/cambiarPassword.jsp?error=short");
                return;
            }

            HttpSession ses = request.getSession(false);
            if (ses == null) {
                response.sendRedirect(request.getContextPath() + "/Vista/recuperar.jsp");
                return;
            }
            Object oCorreo = ses.getAttribute("recupEmail");
            String correo = oCorreo != null ? oCorreo.toString() : null;
            if (correo == null) {
                response.sendRedirect(request.getContextPath() + "/Vista/recuperar.jsp");
                return;
            }

            String hashed = BCrypt.hashpw(pass1, BCrypt.gensalt(12));
            DAOUsuarios dao = new DAOUsuarios();
            boolean ok = dao.actualizarContrasena(correo, hashed);
            if (ok) {
                // limpiar atributos relacionados con la recuperación
                ses.removeAttribute("recupCodigo");
                ses.removeAttribute("recupExpira");
                ses.removeAttribute("recupEmail");
                ses.removeAttribute("recupIntentos");
                response.sendRedirect(request.getContextPath() + "/Vista/login.jsp?recup=ok");
            } else {
                response.sendRedirect(request.getContextPath() + "/Vista/cambiarPassword.jsp?error=bd");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/Vista/cambiarPassword.jsp?error=ex");
        }
    }

    // doGet / doPost
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
