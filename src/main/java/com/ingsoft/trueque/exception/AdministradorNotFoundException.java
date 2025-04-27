package com.ingsoft.trueque.exception;

import jakarta.persistence.EntityNotFoundException;

public class AdministradorNotFoundException extends EntityNotFoundException {
    public AdministradorNotFoundException(String message) {
        super(message);
    }
}
