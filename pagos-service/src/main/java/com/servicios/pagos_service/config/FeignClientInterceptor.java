package com.servicios.pagos_service.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class FeignClientInterceptor{

    /* 
    @Override
    public void apply(RequestTemplate template) {

        ServletRequestAttributes attrs =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attrs == null) return;

        HttpServletRequest request = attrs.getRequest();
        String auth = request.getHeader("Authorization");

        if (auth != null) {
            System.out.println("[FEIGN] Reenviando Authorization: " + auth);
            template.header("Authorization", auth);
        }
    }*/
}
