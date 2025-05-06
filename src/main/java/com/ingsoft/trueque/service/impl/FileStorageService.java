package com.ingsoft.trueque.service.impl;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

import java.util.UUID;

@Service
public class FileStorageService {

    private final String uploadDir = "uploads/articulos";

    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMINISTRADOR')")
    public String guardarImagen(MultipartFile archivo) throws IOException {
        if (archivo.isEmpty()) {
            throw new IllegalArgumentException("El archivo está vacío");
        }

        // Asegurarse de que la carpeta exista
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Crear un nombre único para el archivo
        String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename();

        // Ruta completa donde guardar
        Path filePath = uploadPath.resolve(nombreArchivo);

        // Guardar el archivo
        Files.copy(archivo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Retornar la ruta relativa que guardarás en la BD
        return "uploads/articulos/" + nombreArchivo;
    }
}
