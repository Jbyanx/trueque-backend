package com.ingsoft.trueque.dto.response;

import com.ingsoft.trueque.model.util.EstadoArticulo;

import java.io.Serializable;

public record ReporteSimple(
        Long id,
        String descripcion,
        EstadoArticulo estado,
        String autor
) implements Serializable {
}
