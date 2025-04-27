package com.ingsoft.trueque.dto.request;

import jakarta.validation.constraints.*;

import java.io.Serializable;

public record SaveResenha(
        @Min(1)
        @Max(5)
        Integer puntuacion,
        @NotBlank
        String descripcion,
        @NotNull @Positive
        Long idAutor,
        @NotNull @Positive
        Long idIntercambio
) implements Serializable {
}
