package com.ismail.forum.helper;

import com.ismail.forum.entity.*;
import com.ismail.forum.model.*;

import java.util.ArrayList;
import java.util.List;

public class Response {

    public static TagResponse convertTagToResponse(Tag tag) {
        return new TagResponse(
                tag.getId(),
                tag.getName()
        );
    }

    public static UserResponse convertUserToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public static CommentResponse convertCommentToResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getComment(),
                comment.getCreatedAt(),
                convertUserToResponse(comment.getUser())
        );
    }

    public static PostResponse convertPostToResponse(Post post) {
        List<TagResponse> tags = new ArrayList<TagResponse>();
        post.getTags().forEach(tag -> tags.add(convertTagToResponse(tag)));

        List<CommentResponse> comments = new ArrayList<>();
        post.getComments().forEach(comment -> comments.add(convertCommentToResponse(comment)));

        return new PostResponse(
                post.getId(),
                post.getPost(),
                post.getCreatedAt(),
                comments.size(),
                convertUserToResponse(post.getUser()),
                tags,
                comments
        );
    }

    public static PostListResponse convertPostToCollectionResponse(PostMapping postMapping) {
        return new PostListResponse(
                postMapping.getId(),
                postMapping.getPost(),
                postMapping.getCreatedAt(),
                postMapping.getCommentCount(),
                new UserResponse(
                        postMapping.getUserId(),
                        postMapping.getName(),
                        postMapping.getEmail()
                )
        );
    }

    public static CustomResponse convertPostToCustomResponse(Post post) {
        return new CustomResponse(
                post.getId(),
                post.getPost()
        );
    }

}
