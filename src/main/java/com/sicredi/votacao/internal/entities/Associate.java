package com.sicredi.votacao.internal.entities;

public class Associate {

    private String id;
    private String cpf;

    public Associate() {
    }

    public String getId() {
        return id;
    }

    public Associate setId(String id) {
        this.id = id;
        return this;
    }

    public String getCpf() {
        return cpf;
    }

    public Associate setCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }
}