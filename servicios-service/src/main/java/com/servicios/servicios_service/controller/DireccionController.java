package com.servicios.servicios_service.controller;

import com.servicios.servicios_service.model.DireccionCliente;
import com.servicios.servicios_service.service.DireccionService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/direccion")
public class DireccionController {

    @Autowired
    private DireccionService direccionService;

    @PostMapping("/agregar")
    @ResponseBody
    public Map<String, Object> agregarDireccion(@RequestParam String direccionCompleta,
                                                @RequestParam(required = false) String referencia,
                                                @RequestParam Integer idDistrito,
                                                @RequestParam String distritoNombre,
                                                HttpServletRequest request) {
        Integer clienteId = (Integer) request.getAttribute("clienteId");
    
        DireccionCliente nueva = new DireccionCliente();
        nueva.setDireccion(direccionCompleta);
        nueva.setReferencia(referencia);
        nueva.setEstadoDireccion("Activo");
        nueva.setClienteID(clienteId);
        nueva.setDistritoID(idDistrito);
    
        direccionService.registrarDireccion(nueva);
    
        Map<String, Object> direccionMap = new HashMap<>();
        direccionMap.put("direccionID", nueva.getDireccionID());
        direccionMap.put("direccion", nueva.getDireccion());
        direccionMap.put("referencia", nueva.getReferencia());
        direccionMap.put("distritoNombre", distritoNombre); // ← viene del formulario
    
        System.out.println(distritoNombre);

        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("direccion", direccionMap);
        return resp;
    }
    
    
    @PostMapping("/seleccionar")
    @ResponseBody
    public Map<String, Object> seleccionarDireccion(@RequestParam Integer idDireccion,
                                                    HttpServletRequest request) {
    
        // Aquí podrías guardar en sesión o en un atributo temporal
        request.getSession().setAttribute("direccionSeleccionadaId", idDireccion);
    
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("direccionId", idDireccion);
        return resp;
    }
    
    
}
