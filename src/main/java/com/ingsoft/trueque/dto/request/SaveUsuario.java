package com.ingsoft.trueque.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveUsuario{
        @NotBlank
        private String nombre;
        @NotBlank
        private String apellido;
        @Email
        private String correo;
        @NotBlank
        private String clave;
}
