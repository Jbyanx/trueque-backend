package com.ingsoft.trueque.dto.request;

import java.io.Serializable;

public record SaveNotificacion(
        String mensaje,
        Long id_receptor
) implements Serializable {
}
