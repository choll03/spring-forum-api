package com.ismail.forum.model;

public class CustomResponse {

    private Integer id;
    private String post;

    public CustomResponse() {
    }

    public CustomResponse(Integer id, String post) {
        this.id = id;
        this.post = post;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
