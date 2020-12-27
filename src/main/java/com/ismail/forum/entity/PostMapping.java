package com.ismail.forum.entity;

import java.util.Date;

public class PostMapping {
    private Integer id;
    private String post;
    private Date createdAt;
    private Integer userId;
    private String name;
    private String email;
    private Integer commentCount;

    public PostMapping() {
    }

    public PostMapping(Integer id, String post, Date createdAt, Integer userId, String name, String email, Integer commentCount) {
        this.id = id;
        this.post = post;
        this.createdAt = createdAt;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.commentCount = commentCount;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }
}
