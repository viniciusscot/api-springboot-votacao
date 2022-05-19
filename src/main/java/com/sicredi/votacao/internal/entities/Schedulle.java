package com.sicredi.votacao.internal.entities;

public class Schedulle {

    private String id;
    private String name;

    public Schedulle() {
    }

    public String getId() {
        return id;
    }

    public Schedulle setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Schedulle setName(String name) {
        this.name = name;
        return this;
    }
}
