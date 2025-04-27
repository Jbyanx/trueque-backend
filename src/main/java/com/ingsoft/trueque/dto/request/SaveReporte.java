package com.ingsoft.trueque.dto.request;

import com.ingsoft.trueque.model.util.EstadoReporte;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;

public record SaveReporte(
        @NotBlank
        String descripcion,
        @NotNull @Positive
        Long idAutor,
        @NotNull
        EstadoReporte estado,
        @NotNull @Positive
        Long idArticulo
) implements Serializable {
}
