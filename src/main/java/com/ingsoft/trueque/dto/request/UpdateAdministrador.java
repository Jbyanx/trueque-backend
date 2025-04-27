package com.ingsoft.trueque.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record UpdateAdministrador(
        @NotBlank
        String nombre,
        @NotBlank
        String apellido
) implements Serializable {
}
