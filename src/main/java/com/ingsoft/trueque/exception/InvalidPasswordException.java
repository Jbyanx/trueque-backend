package com.ingsoft.trueque.exception;

import jakarta.persistence.EntityNotFoundException;

public class InvalidPasswordException extends EntityNotFoundException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
