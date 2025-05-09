package com.ingsoft.trueque.dto.request;

import org.springframework.data.domain.Pageable;

public record ArticuloFiltroRequest(
        String categoria,
        String nombre,
        String estado,
        Pageable pageable
) {
}
