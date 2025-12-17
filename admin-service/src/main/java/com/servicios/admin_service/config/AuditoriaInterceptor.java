package com.servicios.admin_service.config;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//import com.servicios.admin_service.service.AuditoriaService;

@Component
public class AuditoriaInterceptor implements HandlerInterceptor {

    //@Autowired
    //private AuditoriaService auditoriaService;

    @SuppressWarnings("null")
    /*@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return true;
    }*/
   @Override
    public boolean preHandle(HttpServletRequest request, 
                                HttpServletResponse response, 
                                Object handler) {
        // Propagamos datos básicos del request para que los Services los usen en auditoría 
        request.setAttribute("endpoint", request.getRequestURI()); 
        request.setAttribute("httpMethod", request.getMethod()); 
        
        // AdminAuthInterceptor ya debería haber puesto usuarioRol y usuarioId 
        String rol = (String) request.getAttribute("usuarioRol"); 
        Long usuarioId = (Long) request.getAttribute("usuarioId"); 
        
        System.out.println("=== Interceptor Auditoría ACTIVADO ==="); 
        System.out.println("Método HTTP=" + request.getMethod() + ", URI=" + request.getRequestURI()); 
        System.out.println("ROL=" + rol + ", usuarioId=" + usuarioId); 

        return true; 
    }

    @SuppressWarnings("null")
    @Override 
    public void afterCompletion(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    Object handler, 
                                    Exception ex) { 
        // Ya no enviamos auditoría aquí. 
        // Solo dejamos los atributos disponibles para los Controllers/Services. 
    }
}
