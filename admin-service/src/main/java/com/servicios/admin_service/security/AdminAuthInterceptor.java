package com.servicios.admin_service.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AdminAuthInterceptor implements HandlerInterceptor {

    // Dependencia que valida el token y los permisos
    private final AuthValidator authValidator;

    public AdminAuthInterceptor(AuthValidator authValidator) {
        this.authValidator = authValidator;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        System.out.println("\n\n=======INTERCEPTOR ADMIN-SERVICE=======");
        System.out.println("LOG: URL solicitada → " + request.getRequestURI());

        // Se intenta obtener el token desde el encabezado Authorization
        String header = request.getHeader("Authorization");
        String token = null;
        System.out.println("LOG: Authorization Header recibido → " + header);

        // Si el header contiene un Bearer token, lo extraemos
        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7); // Eliminamos "Bearer "
            System.out.println("LOG: Token detectado en HEADER:");
            System.out.println(token);

        // Si no está en el header, se buscan cookies en la petición
        } else if (request.getCookies() != null) {

            System.out.println("LOG: Buscando token en COOKIES...");
            for (Cookie c : request.getCookies()) {
                System.out.println("Cookie encontrada → " + c.getName() + " = " + c.getValue());

                // Si existe una cookie llamada "jwt", se toma como token
                if ("jwt".equals(c.getName())) {
                    token = c.getValue();
                    System.out.println("LOG: Token encontrado en cookie JWT:");
                    System.out.println(token);
                    break;
                }
            }
        }

        // Si no encontramos token en header ni cookie, denegamos acceso
        if (token == null) {
            System.out.println("LOG: No se encontró token en header ni cookie.");
            response.sendRedirect("/no-autorizado");
            return false;
        }

        try {
            // Validamos la firma y contenido del token
            System.out.println("LOG: Validando token...");
            Claims claims = authValidator.validarToken("Bearer " + token);

            // Verificamos que el rol contenido en el token sea ADMIN
            System.out.println("LOG: Verificando rol administrador...");
            authValidator.permitirAdmin(claims);
            System.out.println("LOG: Token válido y rol permitido — acceso concedido");

            // Guardamos datos del usuario extraídos del token en el request
            request.setAttribute("usuarioEmail", claims.getSubject());
            request.setAttribute("usuarioRol", claims.get("rol", String.class));

            // También tratamos de obtener el usuarioId desde los claims
            Integer usuarioIdInt = claims.get("usuarioId", Integer.class);

            if (usuarioIdInt != null) {
                // Si existe, lo convertimos a long y lo enviamos
                request.setAttribute("usuarioId", usuarioIdInt.longValue());
                System.out.println("LOG: usuarioId propagado al request → " + usuarioIdInt);
            } else {
                // Si no existe, enviamos un ID por defecto
                request.setAttribute("usuarioId", 0L);
                System.out.println("LOG: usuarioId no encontrado en claims, se setea 0");
            }

            System.out.println();
            return true; // Permite continuar la ejecución del endpoint

        } catch (Exception e) {
            // Si el token es inválido o el rol no es permitido, se bloquea el acceso
            System.out.println("ERROR: " + e.getMessage());
            response.sendRedirect("/no-autorizado");
            return false;
        }
    }

}
