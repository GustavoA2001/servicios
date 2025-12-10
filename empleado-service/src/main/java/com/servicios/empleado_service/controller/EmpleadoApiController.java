package com.servicios.empleado_service.controller;

import com.servicios.empleado_service.model.Empleado;
import com.servicios.empleado_service.service.EmpleadoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/empleado")
public class EmpleadoApiController {

    private final EmpleadoService empleadoService;

    public EmpleadoApiController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmpleadoById(@PathVariable Integer id) {
        Optional<Empleado> o = empleadoService.findById(id);
        return o.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/me")
    public ResponseEntity<?> getEmpleadoMe(HttpSession session) {
        Object sid = session.getAttribute("empleadoId");
        if (sid == null) {
            return ResponseEntity.status(401).body("No autenticado");
        }
        Integer id = (sid instanceof Integer) ? (Integer) sid : Integer.valueOf(sid.toString());
        return getEmpleadoById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmpleadoById(@PathVariable Integer id,
                                                @RequestBody Empleado payload,
                                                HttpSession session) {
        Object sid = session.getAttribute("empleadoId");
        if (sid == null) return ResponseEntity.status(401).body("No autenticado");
        Integer sessionId = (sid instanceof Integer) ? (Integer) sid : Integer.valueOf(sid.toString());
        if (!sessionId.equals(id)) {
            return ResponseEntity.status(403).body("No autorizado para modificar este perfil");
        }

        Optional<Empleado> cur = empleadoService.findById(id);
        if (cur.isEmpty()) return ResponseEntity.notFound().build();

        Empleado e = cur.get();
        if (payload.getNombre() != null) e.setNombre(payload.getNombre());
        if (payload.getApellido() != null) e.setApellido(payload.getApellido());
        if (payload.getEmail() != null) e.setEmail(payload.getEmail());
        if (payload.getTelefono() != null) e.setTelefono(payload.getTelefono());
        if (payload.getUbicacion() != null) e.setUbicacion(payload.getUbicacion());
        if (payload.getFoto() != null) e.setFoto(payload.getFoto());
        Empleado saved = empleadoService.save(e);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateEmpleadoMe(@RequestBody Empleado payload, HttpSession session) {
        Object sid = session.getAttribute("empleadoId");
        if (sid == null) return ResponseEntity.status(401).body("No autenticado");
        Integer id = (sid instanceof Integer) ? (Integer) sid : Integer.valueOf(sid.toString());
        return updateEmpleadoById(id, payload, session);
    }

    
}
