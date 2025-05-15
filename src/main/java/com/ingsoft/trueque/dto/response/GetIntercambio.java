package com.ingsoft.trueque.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;

public record GetIntercambio(
        Long id,
        String estado,
        LocalDateTime fecha,
        String usuarioUno,
        String usuarioDos,
        String articuloUno,
        String articuloDos,
        String telefonoUsuarioUno,
        String telefonoUsuarioDos,
        Long idIntercambioPadre
) implements Serializable {
}
