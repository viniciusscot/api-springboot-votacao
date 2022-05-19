package com.sicredi.votacao.bootstrap.exceptions;

public class AssociateNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public AssociateNotFoundException(String associadoId) {
        super(String.format("There is no associate registration with code %s", associadoId));
    }
}
