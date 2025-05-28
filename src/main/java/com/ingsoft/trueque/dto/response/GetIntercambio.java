package com.ingsoft.trueque.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;

public record GetIntercambio(
        Long id,
        String estado,
        LocalDateTime fecha,
        Long usuarioUno,
        Long usuarioDos,
        Long articuloDos,
        Long articuloUno,
        String telefonoUsuarioUno,
        String telefonoUsuarioDos,
        Long idIntercambioPadre
) implements Serializable {
}
