package com.sicredi.votacao.internal.entities;

import java.time.OffsetDateTime;

public class Vote {

    private String id;
    private OffsetDateTime horary;
    private Boolean decision;
    private String associateId;
    private String schedulleId;
    private String sessionId;

    public Vote() {
    }

    public String getId() {
        return id;
    }

    public Vote setId(String id) {
        this.id = id;
        return this;
    }

    public OffsetDateTime getHorary() {
        return horary;
    }

    public Vote setHorary(OffsetDateTime horary) {
        this.horary = horary;
        return this;
    }

    public Boolean getDecision() {
        return decision;
    }

    public Vote setDecision(Boolean decision) {
        this.decision = decision;
        return this;
    }

    public String getAssociateId() {
        return associateId;
    }

    public Vote setAssociateId(String associateId) {
        this.associateId = associateId;
        return this;
    }

    public String getSchedulleId() {
        return schedulleId;
    }

    public Vote setSchedulleId(String schedulleId) {
        this.schedulleId = schedulleId;
        return this;
    }

    public String getSessionId() {
        return sessionId;
    }

    public Vote setSessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }
}
