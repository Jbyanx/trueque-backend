package com.ingsoft.trueque.dto.response;

import com.ingsoft.trueque.model.util.EstadoArticulo;

import java.io.Serializable;

public record GetArticulo(
        Long id,
        String nombre,
        String descripcion,
        String urlImagen,
        String  estado,
        String propietario,
        String categoria
) implements Serializable {
}
