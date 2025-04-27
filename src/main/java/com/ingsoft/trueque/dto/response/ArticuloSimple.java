package com.ingsoft.trueque.dto.response;

import java.io.Serializable;

public record ArticuloSimple(
        Long id,
        String nombre,
        String estado
) implements Serializable {
}
