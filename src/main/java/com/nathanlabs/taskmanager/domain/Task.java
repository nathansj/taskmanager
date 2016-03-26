package com.nathanlabs.taskmanager.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class Task {

    public static enum Priority {
        ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN
    }

    private String id;
    @NotNull
    private String subject;
    private Date dueDate;
    private float durationInHours;
    private Priority priority;

    public Task() {

    }

    public Task(String id, String subject, Date dueDate, float duration, Priority importance) {
        this.id = id;
        this.subject = subject;
        this.dueDate = dueDate;
        this.durationInHours = duration;
        this.priority = importance;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public float getDurationInHours() {
        return durationInHours;
    }

    public void setDurationInHours(float durationInHours) {
        this.durationInHours = durationInHours;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}