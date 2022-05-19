package com.sicredi.votacao.bootstrap.exceptions;

public class SchedulleNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public SchedulleNotFoundException(String schedulleId) {
        super(String.format("There is no schedulle entry with code %s", schedulleId));
    }

}
