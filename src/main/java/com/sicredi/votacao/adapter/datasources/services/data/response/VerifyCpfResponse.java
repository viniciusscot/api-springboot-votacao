package com.sicredi.votacao.adapter.datasources.services.data.response;

public class VerifyCpfResponse {

    private StatusEnum status;

    public VerifyCpfResponse() {
    }

    public StatusEnum getStatus() {
        return status;
    }

    public VerifyCpfResponse setStatus(StatusEnum status) {
        this.status = status;
        return this;
    }

    public enum StatusEnum {
        ABLE_TO_VOTE, UNABLE_TO_VOTE
    }
}
