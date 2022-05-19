package com.sicredi.votacao.bootstrap.exceptions;

public class EntityInUseException extends BusinessException {
    private static final long serialVersionUID = 1l;

    public EntityInUseException(String mensagem) {
        super(mensagem);
    }
}
