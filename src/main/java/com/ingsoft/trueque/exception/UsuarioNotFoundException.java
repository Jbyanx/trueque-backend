package com.ingsoft.trueque.exception;

import jakarta.persistence.EntityNotFoundException;

public class UsuarioNotFoundException extends EntityNotFoundException {
    public UsuarioNotFoundException(String message) {
        super(message);
    }
}
