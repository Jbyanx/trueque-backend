package com.ingsoft.trueque.exception;

import jakarta.persistence.EntityNotFoundException;

public class CategoriaNotFoundException extends EntityNotFoundException {
    public CategoriaNotFoundException(String message) {
        super(message);
    }
}
