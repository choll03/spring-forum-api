package com.ismail.forum.model;

import java.util.List;

public class PostRequest {

    private String post;
    private Integer userId;
    private List<Integer> tags;

    public PostRequest() {
    }

    public PostRequest(String post, Integer userId, List<Integer> tags) {
        this.post = post;
        this.userId = userId;
        this.tags = tags;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Integer> getTags() {
        return tags;
    }

    public void setTags(List<Integer> tags) {
        this.tags = tags;
    }
}
