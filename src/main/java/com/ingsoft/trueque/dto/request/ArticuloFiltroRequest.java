package com.ingsoft.trueque.dto.request;

import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public record ArticuloFiltroRequest(
        String categoria,
        String nombre,
        String estado,
        Pageable pageable
) {
}
