package com.ingsoft.trueque.dto.response;

import java.io.Serializable;

public record ReporteSimple(
        Long id,
        String descripcion,
        String estado,
        String autor
) implements Serializable {
}
