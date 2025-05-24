package com.ingsoft.trueque.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;

public record GetNotificacion(
        Long id,
        String mensaje,
        Boolean leida,
        LocalDateTime fecha,
        Long id_receptor
) implements Serializable {
}
