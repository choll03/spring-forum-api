package com.ismail.forum.model;

public class TagRequest {

    private String name;

    public TagRequest() {}

    public TagRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
