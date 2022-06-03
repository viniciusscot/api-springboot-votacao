package com.sicredi.votacao.bootstrap.log;

public enum LogLevel {
    TRACE(0),
    FATAL(2),
    ERROR(3),
    WARN(4),
    INFO(6),
    DEBUG(7);

    private final Integer value;

    LogLevel(Integer value) {
        this.value = value;
    }

    public Integer getValue(){
        return value;
    }
}
