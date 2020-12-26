package com.ismail.forum.model;

import javax.validation.constraints.NotBlank;

public class TagRequest {

    @NotBlank(message = "Name not be blank")
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
