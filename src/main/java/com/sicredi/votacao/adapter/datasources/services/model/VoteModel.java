package com.sicredi.votacao.adapter.datasources.services.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;

@Document(collection = "Vote")
public class VoteModel {

    @Id
    private String id;

    private OffsetDateTime horary;
    private Boolean decision;
    private String associateId;
    private String schedulleId;
    private String sessionId;

    public VoteModel() {
    }

    public String getId() {
        return id;
    }

    public VoteModel setId(String id) {
        this.id = id;
        return this;
    }

    public OffsetDateTime getHorary() {
        return horary;
    }

    public VoteModel setHorary(OffsetDateTime horary) {
        this.horary = horary;
        return this;
    }

    public Boolean getDecision() {
        return decision;
    }

    public VoteModel setDecision(Boolean decision) {
        this.decision = decision;
        return this;
    }

    public String getAssociateId() {
        return associateId;
    }

    public VoteModel setAssociateId(String associateId) {
        this.associateId = associateId;
        return this;
    }

    public String getSchedulleId() {
        return schedulleId;
    }

    public VoteModel setSchedulleId(String schedulleId) {
        this.schedulleId = schedulleId;
        return this;
    }

    public String getSessionId() {
        return sessionId;
    }

    public VoteModel setSessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }
}
