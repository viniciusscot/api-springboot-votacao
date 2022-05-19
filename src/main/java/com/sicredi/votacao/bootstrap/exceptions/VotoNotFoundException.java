package com.sicredi.votacao.bootstrap.exceptions;

public class VotoNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public VotoNotFoundException(String voteId) {
        super(String.format("There is no voter registration with code %s", voteId));
    }

}
