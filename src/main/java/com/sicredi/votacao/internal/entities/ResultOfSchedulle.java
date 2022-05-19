package com.sicredi.votacao.internal.entities;

public class ResultOfSchedulle {

    private Session session;
    private Long votes;
    private Long votesYes;
    private Long votesNo;
    private String result;

    public ResultOfSchedulle() {
    }

    public Session getSession() {
        return session;
    }

    public ResultOfSchedulle setSession(Session session) {
        this.session = session;
        return this;
    }

    public Long getVotes() {
        return votes;
    }

    public ResultOfSchedulle setVotes(Long votes) {
        this.votes = votes;
        return this;
    }

    public Long getVotesYes() {
        return votesYes;
    }

    public ResultOfSchedulle setVotesYes(Long votesYes) {
        this.votesYes = votesYes;
        return this;
    }

    public Long getVotesNo() {
        return votesNo;
    }

    public ResultOfSchedulle setVotesNo(Long votesNo) {
        this.votesNo = votesNo;
        return this;
    }

    public String getResult() {
        return result;
    }

    public ResultOfSchedulle setResult(String result) {
        this.result = result;
        return this;
    }
}
