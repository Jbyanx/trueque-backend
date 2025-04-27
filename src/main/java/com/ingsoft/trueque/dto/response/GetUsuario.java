package com.ingsoft.trueque.dto.response;

import java.io.Serializable;
import java.util.List;

public record GetUsuario(
        Long id,
        String nombre,
        String apellido,
        String correo,
        List<ArticuloSimple> articulos
) implements Serializable {
}
