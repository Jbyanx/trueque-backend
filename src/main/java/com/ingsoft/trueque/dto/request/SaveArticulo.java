package com.ingsoft.trueque.dto.request;

import com.ingsoft.trueque.model.util.EstadoArticulo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record SaveArticulo(
        @NotBlank
        String nombre,
        String descripcion,
        String rutaImagen,
        @NotNull
        Long idCategoria,
        @NotNull
        EstadoArticulo estado,
        @NotNull
        Long idPropietario
) implements Serializable {
}
