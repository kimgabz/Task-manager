package com.intellij.assessment.model;

/**
 * Created by kimharold on 5/16/17.
 */

public class Task {

    private int id;
    private long dbid;
    private String title;
    private String details;

    public Task() {
    }

    public Task(String title, String details) {
        this.title = title;
        this.details = details;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDbid() {
        return dbid;
    }

    public void setDbid(long dbid) {
        this.dbid = dbid;
    }
}
