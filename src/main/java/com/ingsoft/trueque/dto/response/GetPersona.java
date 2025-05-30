package com.ingsoft.trueque.dto.response;

import java.io.Serializable;

public record GetPersona(
        Long id,
        String nombre,
        String apellido,
        String telefono,
        String correo,
        String clave
) implements Serializable {
}
