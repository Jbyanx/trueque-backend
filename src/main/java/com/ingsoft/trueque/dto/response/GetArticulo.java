package com.ingsoft.trueque.dto.response;

import java.io.Serializable;

public record GetArticulo(
        Long id,
        String nombre,
        String descripcion,
        String urlImagen,
        String  estado,
        String idPropietario,
        Long idCategoria
) implements Serializable {
}
