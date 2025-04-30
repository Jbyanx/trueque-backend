package com.ingsoft.trueque.exception;

import jakarta.persistence.EntityNotFoundException;

public class IntercambioNotFoundException extends EntityNotFoundException {
    public IntercambioNotFoundException(String message) {
        super(message);
    }
}
