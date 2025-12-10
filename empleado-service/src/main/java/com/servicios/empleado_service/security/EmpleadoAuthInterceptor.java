package com.servicios.empleado_service.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class EmpleadoAuthInterceptor implements HandlerInterceptor {

    private final AuthValidator authValidator;

    public EmpleadoAuthInterceptor(AuthValidator authValidator) {
        this.authValidator = authValidator;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        System.out.println("\n\n======= INTERCEPTOR EMPLEADO-SERVICE =======");
        System.out.println("LOG: URL solicitada → " + request.getRequestURI());

        String header = request.getHeader("Authorization");
        String token = null;

        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
        } else if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if ("jwt".equals(c.getName())) {
                    token = c.getValue();
                    break;
                }
            }
        }

        if (token == null) {
            response.sendRedirect("/no-autorizado");
            return false;
        }

        try {
            Claims claims = authValidator.validarToken("Bearer " + token);

            // Verificar rol EMPLEADO o TRABAJADOR
            String rol = claims.get("rol", String.class);
            if (!"EMPLEADO".equalsIgnoreCase(rol) && !"TRABAJADOR".equalsIgnoreCase(rol)) {
                throw new RuntimeException("Acceso denegado. Rol no permitido");
            }

            // Propagar atributos al request
            request.setAttribute("empleadoEmail", claims.getSubject());
            request.setAttribute("empleadoRol", rol);
            request.setAttribute("empleadoId", claims.get("usuarioId", Integer.class));

            return true;

        } catch (Exception e) {
            response.sendRedirect("/no-autorizado");
            return false;
        }
    }
}
