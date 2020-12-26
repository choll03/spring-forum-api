package com.ismail.forum.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CommentRequest {

    @NotBlank(message = "Comment is required")
    private String comment;

    @NotNull(message = "User is required")
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
