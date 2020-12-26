package com.ismail.forum.model;

public class CommentRequest {

    private String comment;
    private Integer userId;

    public CommentRequest() {
    }

    public CommentRequest(String comment, Integer userId) {
        this.comment = comment;
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
