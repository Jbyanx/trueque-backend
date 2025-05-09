package com.ingsoft.trueque.dto.request;

import com.ingsoft.trueque.model.util.EstadoIntercambio;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;

public record SaveIntercambio(
        @Positive
        Long idIntercambioPadre,
        @NotNull @Positive
        Long idUsuarioDos,
        @NotNull @Positive
        Long idArticuloUno,
        @NotNull @Positive
        Long idArticuloDos
) implements Serializable {
}
