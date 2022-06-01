package com.sicredi.votacao.bootstrap.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problem {

    private Integer status;
    private LocalDateTime timestamp;
    private String type;
    private String title;
    private String detail;
    private String userMessage;
    private List<Object> objects;

    public Problem() {
        this.objects = new ArrayList<>();
    }

    public Integer getStatus() {
        return status;
    }

    public Problem setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Problem setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getType() {
        return type;
    }

    public Problem setType(String type) {
        this.type = type;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Problem setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDetail() {
        return detail;
    }

    public Problem setDetail(String detail) {
        this.detail = detail;
        return this;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public Problem setUserMessage(String userMessage) {
        this.userMessage = userMessage;
        return this;
    }

    public List<Object> getObjects() {
        return objects;
    }

    public Problem setObjects(List<Object> objects) {
        this.objects = objects;
        return this;
    }

    public static class Object {

        private String name;
        private String userMessage;

        public Object() {
        }

        public String getName() {
            return name;
        }

        public Object setName(String name) {
            this.name = name;
            return this;
        }

        public String getUserMessage() {
            return userMessage;
        }

        public Object setUserMessage(String userMessage) {
            this.userMessage = userMessage;
            return this;
        }
    }

}
