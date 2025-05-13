package com.ingsoft.trueque.dto.response;

import java.io.Serializable;

public record GetIntercambioSimple(
        String usuarioUno,
        String usuarioDos,
        String articuloUno,
        String articuloDos
) implements Serializable {
}
