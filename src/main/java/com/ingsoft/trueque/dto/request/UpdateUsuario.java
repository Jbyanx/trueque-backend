package com.ingsoft.trueque.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUsuario{
        @NotBlank
        private String nombre;
        @NotBlank
        private String apellido;
        @NotBlank
        private String telefono;
        private MultipartFile fotoPerfil; // Foto de perfil
}
