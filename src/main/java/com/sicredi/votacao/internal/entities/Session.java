package com.sicredi.votacao.internal.entities;

import java.math.BigInteger;
import java.time.OffsetDateTime;

public class Session {

    private String id;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private Boolean finished;
    private BigInteger durationInMinutes;
    private String schedulleId;

    public Session() {
        this.finished = Boolean.FALSE;
    }

    public String getId() {
        return id;
    }

    public Session setId(String id) {
        this.id = id;
        return this;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public Session setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    public Session setEndDate(OffsetDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public Boolean getFinished() {
        return finished;
    }

    public Session setFinished(Boolean finished) {
        this.finished = finished;
        return this;
    }

    public BigInteger getDurationInMinutes() {
        return durationInMinutes;
    }

    public Session setDurationInMinutes(BigInteger durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
        return this;
    }

    public String getSchedulleId() {
        return schedulleId;
    }

    public Session setSchedulleId(String schedulleId) {
        this.schedulleId = schedulleId;
        return this;
    }
}
