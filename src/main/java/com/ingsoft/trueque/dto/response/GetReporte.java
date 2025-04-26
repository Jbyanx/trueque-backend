package com.ingsoft.trueque.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;

public record GetReporte(
        Long id,
        String descripcion,
        String estado,
        String autor,
        String articulo,
        LocalDateTime fecha
) implements Serializable {
}
