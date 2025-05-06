package com.ingsoft.trueque.dto.response;

import java.io.Serializable;
import java.util.List;

public record GetUsuarioRegistrado(
        Long id,
        String correo,
        String nombre,
        String rol
) implements Serializable {
}
