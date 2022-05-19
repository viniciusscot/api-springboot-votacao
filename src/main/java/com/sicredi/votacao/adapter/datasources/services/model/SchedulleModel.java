package com.sicredi.votacao.adapter.datasources.services.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Schedulle")
public class SchedulleModel {

    @Id
    private String id;

    private String name;

    public SchedulleModel() {
    }

    public String getId() {
        return id;
    }

    public SchedulleModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SchedulleModel setName(String name) {
        this.name = name;
        return this;
    }
}
