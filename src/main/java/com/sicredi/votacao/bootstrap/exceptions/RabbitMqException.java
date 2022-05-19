package com.sicredi.votacao.bootstrap.exceptions;

public class RabbitMqException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RabbitMqException(String mensagem) {
        super(mensagem);
    }

    public RabbitMqException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

}