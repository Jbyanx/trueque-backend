package com.ingsoft.trueque.exception;

import jakarta.persistence.EntityNotFoundException;

public class ArticuloNotFoundException extends EntityNotFoundException {
    public ArticuloNotFoundException(String message) {
        super(message);
    }
}
