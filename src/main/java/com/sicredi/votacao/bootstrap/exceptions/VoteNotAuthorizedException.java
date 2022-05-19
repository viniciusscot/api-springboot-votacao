package com.sicredi.votacao.bootstrap.exceptions;

public class VoteNotAuthorizedException extends BusinessException {
    private static final long serialVersionUID = 1l;

    public VoteNotAuthorizedException(String message) {
        super(message);
    }
}