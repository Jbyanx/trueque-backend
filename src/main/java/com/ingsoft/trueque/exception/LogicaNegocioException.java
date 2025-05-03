package com.ingsoft.trueque.exception;

import jakarta.persistence.EntityNotFoundException;

public class LogicaNegocioException extends EntityNotFoundException {
    public LogicaNegocioException(String message) {
        super(message);
    }
}
