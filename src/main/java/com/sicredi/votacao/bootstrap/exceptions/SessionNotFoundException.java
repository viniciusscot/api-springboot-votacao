package com.sicredi.votacao.bootstrap.exceptions;

public class SessionNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public SessionNotFoundException(String schedulleId) {
        super(String.format("There is no session for schedulle code %s", schedulleId));
    }

}
