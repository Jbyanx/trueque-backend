package com.ingsoft.trueque.dto.response;

import java.io.Serializable;

public record GetResenha(
        Long id,
        Integer puntuacion,
        String descripcion,
        String  idAutor,
        Long idIntercambio
) implements Serializable {
}
