package com.ingsoft.trueque.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;

public record GetReporte(
        Long id,
        String descripcion,
        String estado,
        Long idAutor,
        Long idArticulo,
        LocalDateTime fecha
) implements Serializable {
}
