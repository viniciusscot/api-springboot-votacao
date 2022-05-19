package com.sicredi.votacao.internal.entities;

public class VerifyCpf {

    private StatusEnum status;

    public VerifyCpf() {
    }

    public StatusEnum getStatus() {
        return status;
    }

    public VerifyCpf setStatus(StatusEnum status) {
        this.status = status;
        return this;
    }

    public enum StatusEnum {
        ABLE_TO_VOTE, UNABLE_TO_VOTE
    }
}
