package com.ingsoft.trueque.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

import java.util.UUID;

@Service
@Log4j2
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

    public void eliminarImagen(String rutaRelativa) {
        try {
            Path rutaCompleta = Paths.get(rutaRelativa).toAbsolutePath();
            Files.deleteIfExists(rutaCompleta);
        } catch (IOException e) {
            log.error("No se pudo eliminar la imagen: " + rutaRelativa);
        }
    }

}
