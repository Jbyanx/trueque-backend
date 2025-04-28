package com.ingsoft.trueque.exception;

import jakarta.persistence.EntityNotFoundException;

public class ReporteNotFoundException extends EntityNotFoundException {
    public ReporteNotFoundException(String message) {
        super(message);
    }
}
