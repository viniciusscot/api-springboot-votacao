package com.sicredi.votacao.bootstrap.exceptions;

public class VoteAlreadyComputedException extends BusinessException {
    private static final long serialVersionUID = 1l;

    public VoteAlreadyComputedException(String message) {
        super(message);
    }
}