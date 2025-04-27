package com.ingsoft.trueque.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record SaveCategoria(
        @NotBlank
        String nombre,
        @NotBlank
        String descripcion
) implements Serializable {
}
