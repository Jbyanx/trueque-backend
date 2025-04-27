package com.ingsoft.trueque.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record SaveUsuario(
        @NotBlank
        String nombre,
        @NotBlank
        String apellido,
        @Email
        String correo,
        @NotBlank
        String clave
) implements Serializable {
}
