package com.ingsoft.trueque.exception;

import jakarta.persistence.EntityNotFoundException;

public class ResenhaNotFoundException extends EntityNotFoundException {
    public ResenhaNotFoundException(String message) {
        super(message);
    }
}
