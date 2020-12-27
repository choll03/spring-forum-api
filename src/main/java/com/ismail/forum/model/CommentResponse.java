package com.ismail.forum.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommentResponse {

    private Integer id;
    private String comment;
    private Date createdAt;
    private UserResponse user;

    public CommentResponse() {
    }

    public CommentResponse(Integer id, String comment, Date createdAt, UserResponse user) {
        this.id = id;
        this.comment = comment;
        this.createdAt = createdAt;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
