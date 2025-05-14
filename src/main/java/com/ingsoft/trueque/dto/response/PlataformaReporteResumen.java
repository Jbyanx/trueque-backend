package com.ingsoft.trueque.dto.response;

import java.io.Serializable;

public record PlataformaReporteResumen(
        Long totalUsuarios,
        Long totalArticulosIntercambiados,
        Long totalReportes,
        Long reportesPendientes,
        Long reportesAtendidos
) implements Serializable {
}
