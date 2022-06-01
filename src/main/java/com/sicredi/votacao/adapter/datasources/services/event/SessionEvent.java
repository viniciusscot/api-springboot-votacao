package com.sicredi.votacao.adapter.datasources.services.event;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.time.OffsetDateTime;

@Document(collection = "Session")
public class SessionEvent {

    @Id
    private String id;
    @CreatedDate
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private Boolean finished;
    private BigInteger durationInMinutes;
    private String schedulleId;

    public SessionEvent() {
    }

    public String getId() {
        return id;
    }

    public SessionEvent setId(String id) {
        this.id = id;
        return this;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public SessionEvent setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    public SessionEvent setEndDate(OffsetDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public Boolean getFinished() {
        return finished;
    }

    public SessionEvent setFinished(Boolean finished) {
        this.finished = finished;
        return this;
    }

    public BigInteger getDurationInMinutes() {
        return durationInMinutes;
    }

    public SessionEvent setDurationInMinutes(BigInteger durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
        return this;
    }

    public String getSchedulleId() {
        return schedulleId;
    }

    public SessionEvent setSchedulleId(String schedulleId) {
        this.schedulleId = schedulleId;
        return this;
    }
}
