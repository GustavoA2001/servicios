package com.servicios.imagen_service.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;

@RestController
@RequestMapping("/images")
public class ImageController {

    @PostMapping
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file)
            throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path path = Paths.get("src/main/resources/static/uploads/usuarios/" + fileName);
        Files.write(path, file.getBytes());

        String url = "http://localhost:8090/uploads/usuarios/" + fileName;
        Map<String, String> response = new HashMap<>();
        response.put("url", url);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{tipo}/{nombre}")
    public ResponseEntity<String> obtenerRutaImagen(@PathVariable String tipo,
            @PathVariable String nombre) {

        System.out.println(tipo +" / "+nombre);
        String ruta = "http://localhost:8090/uploads/" + tipo + "/" + nombre;

        System.out.println(ruta);
        
        // Validar existencia física
        Path filePath = Paths.get("src/main/resources/static/uploads/" + tipo + "/" + nombre);
        if (!Files.exists(filePath)) {
            ruta = "http://localhost:8090/uploads/" + tipo + "/default.jpg";
        }

        return ResponseEntity.ok(ruta);
    }

}