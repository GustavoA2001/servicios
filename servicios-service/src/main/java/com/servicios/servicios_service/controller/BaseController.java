package com.servicios.servicios_service.controller;

import com.servicios.servicios_service.security.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

public abstract class BaseController {

    @Autowired
    private JwtUtil jwtUtil;

    @ModelAttribute("usuarioEmail")
    public String usuarioEmail(HttpServletRequest request) {
        String token = getTokenFromCookies(request);
        if (token != null) {
            Claims claims = jwtUtil.validateToken(token);
            return claims.getSubject();
        }
        return null;
    }

    @ModelAttribute("usuarioRol")
    public String usuarioRol(HttpServletRequest request) {
        String token = getTokenFromCookies(request);
        if (token != null) {
            Claims claims = jwtUtil.validateToken(token);
            return claims.get("rol", String.class);
        }
        return null;
    }

    private String getTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if ("jwt".equals(c.getName())) {
                    return c.getValue();
                }
            }
        }
        return null;
    }
}