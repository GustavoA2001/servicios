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

    /*
     * @PostMapping
     * public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file")
     * MultipartFile file)
     * throws IOException {
     * String fileName = System.currentTimeMillis() + "_" +
     * file.getOriginalFilename();
     * Path path = Paths.get("src/main/resources/static/uploads/servicios/" +
     * fileName);
     * Files.write(path, file.getBytes());
     * 
     * System.out.println("[ImageController] Imagen recibida: " +
     * file.getOriginalFilename());
     * System.out.println("[ImageController] Nombre generado: " + fileName);
     * System.out.println("[ImageController] Guardada en: " +
     * path.toAbsolutePath());
     * 
     * Map<String, String> response = new HashMap<>();
     * response.put("nombre", fileName); // <-- solo nombre
     * System.out.println("[ImageController] Respuesta enviada: " + response);
     * return ResponseEntity.ok(response);
     * }
     */

    @PostMapping
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file)
            throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        // 
        //Path dirPath = Paths.get("src/main/resources/static/uploads/servicios");
        Path dirPath = Paths.get("C:\\Users\\alegr\\OneDrive\\Documentos\\GUSTAVO\\SOA\\servicios\\imagen-service\\src\\main\\resources\\static\\uploads\\servicios");
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
            System.out.println("[ImageController] Carpeta creada: " + dirPath.toAbsolutePath());
        }

        Path path = dirPath.resolve(fileName);
        Files.write(path, file.getBytes());

        System.out.println("[ImageController] Imagen recibida: " + file.getOriginalFilename());
        System.out.println("[ImageController] Nombre generado: " + fileName);
        System.out.println("[ImageController] Guardada en: " + path.toAbsolutePath());

        Map<String, String> response = new HashMap<>();
        response.put("nombre", fileName);
        System.out.println("[ImageController] Respuesta enviada: " + response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{tipo}/{nombre}")
    public ResponseEntity<String> obtenerRutaImagen(@PathVariable String tipo,
            @PathVariable String nombre) {

        System.out.println("[ImageController] Solicitud GET: tipo=" + tipo + " nombre=" + nombre);
        String ruta = "http://localhost:8090/uploads/" + tipo + "/" + nombre;

        System.out.println("[ImageController] Ruta construida: " + ruta);

        Path filePath = Paths.get("src/main/resources/static/uploads/" + tipo + "/" + nombre);
        if (!Files.exists(filePath)) {
            ruta = "http://localhost:8090/uploads/" + tipo + "/default.jpg";
            System.out.println("[ImageController] Archivo no existe, usando default: " + ruta);
        } else {
            System.out.println("[ImageController] Archivo encontrado en: " + filePath.toAbsolutePath());
        }

        return ResponseEntity.ok(ruta);
    }

}