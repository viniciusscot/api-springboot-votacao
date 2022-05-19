package com.sicredi.votacao.bootstrap.exceptions;

public class EntityNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1l;

    public EntityNotFoundException(String message) {
        super(message);
    }

}
