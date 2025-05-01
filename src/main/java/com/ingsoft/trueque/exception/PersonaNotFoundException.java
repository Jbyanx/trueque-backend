package com.ingsoft.trueque.exception;

import jakarta.persistence.EntityNotFoundException;

public class PersonaNotFoundException extends EntityNotFoundException {
    public PersonaNotFoundException(String message) {
        super(message);
    }
}
