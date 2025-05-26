package com.ingsoft.trueque.exception;

import jakarta.persistence.EntityNotFoundException;

public class NotificacionNotFoundException extends EntityNotFoundException {
    public NotificacionNotFoundException(String message) {
        super(message);
    }
}
