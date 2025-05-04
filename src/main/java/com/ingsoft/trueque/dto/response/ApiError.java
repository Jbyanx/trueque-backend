package com.ingsoft.trueque.dto.response;

import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ApiError(
        HttpStatus status,
        String mensaje,
        String backendMessage,
        LocalDateTime dateTime
) implements Serializable {
}
