package com.sicredi.votacao.bootstrap.exceptionhandler;


public enum ProblemType {

    INVALID_DATA("/invalid-data", "Invalid Data"),
    SYSTEM_ERROR("/system-error", "System Error"),
    INVALID_PARAMETER("/invalid-parameter", "Invalid Parameter"),
    MESSAGE_INCOMPREHENSIBLE("/message-incomprehensible", "Mensage Incomprehensible"),
    RESOURCE_NOT_FOUND("/resource-not-found", "Resource Not Found"),
    ENTITY_IN_USE("/entity-in-use", "Entity In Use"),
    BUSINESS_ERROR("/business-error", "Business Rule Violation"),
    VOTE_ALREADY_COMPUTED("/vote-already-computed", "Vote Already Computed"),
    VOTE_NOT_AUTHORIZED("/vote-not-authorized", "Vote Not Authorized");

    private String title;
    private String uri;

    ProblemType(String path, String title) {
        this.uri = "https://sicred-teste.com.br" + path;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getUri() {
        return uri;
    }
}