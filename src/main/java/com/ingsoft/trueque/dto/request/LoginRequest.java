package com.ingsoft.trueque.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record LoginRequest(
        @NotBlank(message = "El correo no debe estar vacio")
        String correo,
        @NotBlank(message = "La clave no debe estar vacia")
        String clave
) implements Serializable {
}
