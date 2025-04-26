package com.ingsoft.trueque.dto.response;

import com.ingsoft.trueque.model.util.EstadoArticulo;

import java.io.Serializable;
import java.time.LocalDateTime;

public record GetIntercambio(
        Long id,
        String estado,
        LocalDateTime fecha,
        String usuarioUno,
        String usuarioDos,
        String articuloUno,
        String articuloDos
) implements Serializable {
}
