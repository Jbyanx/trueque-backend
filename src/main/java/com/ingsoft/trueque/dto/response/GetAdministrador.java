package com.ingsoft.trueque.dto.response;

import java.io.Serializable;
import java.util.List;

public record GetAdministrador(
        Long id,
        String nombre,
        String apellido,
        String correo,
        List<ReporteSimple> reportes
) implements Serializable {
}
