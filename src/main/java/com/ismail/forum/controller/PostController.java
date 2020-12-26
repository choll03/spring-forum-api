package com.ismail.forum.controller;

import com.ismail.forum.model.*;
import com.ismail.forum.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/v1/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public PaginationResponse<List<PostListResponse>> getPost(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword,
            HttpServletRequest request
    ) {
        String url = request.getRequestURL().toString();
        return this.postService.getPosts(new PaginationRequest(url, page), keyword);
    }

    @PostMapping
    public WebResponse<PostResponse> createPost(@RequestBody PostRequest postRequest) {
        return new WebResponse<PostResponse>(
                201,
                "ok",
                this.postService.createPost(postRequest)
        );
    }

    @GetMapping("/{id}")
    public WebResponse<PostResponse> findPost(@PathVariable Integer id) {
        return new WebResponse<PostResponse>(
                200,
                "ok",
                this.postService.findPostById(id)
        );
    }

    @PutMapping("/{id}")
    public WebResponse<PostResponse> updatePost(@PathVariable Integer id, @RequestBody PostRequest postRequest) {
        return new WebResponse<PostResponse>(
                200,
                "ok",
                this.postService.updatePost(id, postRequest)
        );
    }

    @DeleteMapping("/{id}")
    public WebResponse<String> deletePost(@PathVariable Integer id) {
        this.postService.deletePost(id);
        return new WebResponse<String>(
                200,
                "ok",
                null
        );
    }

    @PostMapping("/{postId}/comments")
    public WebResponse<PostResponse> createComment(@PathVariable("postId") Integer postId,@RequestBody CommentRequest commentRequest) {
        return new WebResponse<PostResponse>(
                201,
                "ok",
                this.postService.createComment(postId, commentRequest)
        );
    }

    @PutMapping("/{postId}/comments/{commentId}")
    public WebResponse<PostResponse> updateComment(
            @PathVariable("postId") Integer postId,
            @PathVariable("commentId") Integer commentId,
            @RequestBody CommentRequest commentRequest
            ) {
        return new WebResponse<PostResponse>(
                200,
                "ok",
                this.postService.updateComment(postId, commentId, commentRequest)
        );
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public WebResponse<PostResponse> deleteComment(
            @PathVariable("postId") Integer postId,
            @PathVariable("commentId") Integer commentId
    ) {
        return new WebResponse<PostResponse>(
                200,
                "ok",
                this.postService.deleteComment(postId, commentId)
        );
    }
}