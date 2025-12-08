package com.servicios.admin_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.servicios.admin_service.service.MensajeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

@Component
public class MensajeInterceptor implements HandlerInterceptor {

    @Autowired
    private MensajeService mensajeService;

    @SuppressWarnings("null")
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            List<String> mensajes = mensajeService.obtenerYBorrarTodos();
            System.out.println("[MensajeInterceptor] Mensajes consumidos de la cola: " + mensajes);

            if (!mensajes.isEmpty()) {
                modelAndView.addObject("mensajes", mensajes);
                System.out.println("[MensajeInterceptor] Mensajes agregados al modelo: " + mensajes);
            }
        }
    }
}

