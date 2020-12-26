package com.ismail.forum.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PostListResponse {

    private Integer id;
    private String post;
    private Date createdAt;
    private UserResponse user;

    public PostListResponse() {
    }

    public PostListResponse(Integer id, String post, Date createdAt) {
        this.id = id;
        this.post = post;
        this.createdAt = createdAt;
    }

    public PostListResponse(Integer id, String post, Date createdAt, UserResponse user) {
        this.id = id;
        this.post = post;
        this.createdAt = createdAt;
        this.user = user;
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

    public String getCreatedAt() {
        return new SimpleDateFormat("dd MMMM yyy HH:mm").format(createdAt);
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }
}
