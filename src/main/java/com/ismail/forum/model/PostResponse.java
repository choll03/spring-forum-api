package com.ismail.forum.model;

import java.util.Date;
import java.util.List;

public class PostResponse extends PostListResponse{

    private List<TagResponse> tags;
    private List<CommentResponse> comments;

    public PostResponse() {
    }

    public PostResponse(Integer id, String post, Date createdAt, Integer commentCount,UserResponse user, List<TagResponse> tags, List<CommentResponse> comments) {
        super(id, post, createdAt, commentCount, user);
        this.tags = tags;
        this.comments = comments;
    }

    public List<TagResponse> getTags() {
        return tags;
    }

    public void setTags(List<TagResponse> tags) {
        this.tags = tags;
    }

    public List<CommentResponse> getComments() {
        return comments;
    }

    public void setComments(List<CommentResponse> comments) {
        this.comments = comments;
    }
}
