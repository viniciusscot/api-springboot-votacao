package com.sicredi.votacao.adapter.datasources.services.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Associate")
public class AssociateModel {

    @Id
    private String id;

    private String cpf;

    public AssociateModel() {
    }

    public String getId() {
        return id;
    }

    public AssociateModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getCpf() {
        return cpf;
    }

    public AssociateModel setCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }
}
