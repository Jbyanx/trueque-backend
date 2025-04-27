package com.ingsoft.trueque.dto.response;

import java.io.Serializable;

public record GetCategoria(
        Long id,
        String nombre,
        String descripcion
) implements Serializable {
}
