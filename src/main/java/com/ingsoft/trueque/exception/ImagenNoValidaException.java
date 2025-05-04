package com.ingsoft.trueque.exception;

import jakarta.persistence.EntityNotFoundException;

public class ImagenNoValidaException extends EntityNotFoundException {
    public ImagenNoValidaException(String message) {
        super(message);
    }
}
