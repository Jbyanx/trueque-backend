package com.ingsoft.trueque.exception;

import jakarta.persistence.EntityNotFoundException;

public class AccesoNoPermitidoException extends EntityNotFoundException {
    public AccesoNoPermitidoException(String message) {
        super(message);
    }
}
