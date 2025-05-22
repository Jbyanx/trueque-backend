package com.ingsoft.trueque.dto.request;

import com.ingsoft.trueque.model.util.EstadoArticulo;

public record ArticuloFiltroRequest(
        Long idCategoria,
        String nombre,
        EstadoArticulo estado
) {
}
