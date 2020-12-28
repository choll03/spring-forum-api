package com.ismail.forum.service;

import com.ismail.forum.entity.PostMapping;
import com.ismail.forum.entity.Post;
import com.ismail.forum.error.NotFoundException;
import com.ismail.forum.helper.Response;
import com.ismail.forum.model.*;
import com.ismail.forum.repository.CommentRepository;
import com.ismail.forum.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostService {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    public PaginationResponse<List<PostListResponse>> getPosts(PaginationRequest request, String keyword) {

        String whereClause = "";
        if(!keyword.isEmpty()) {
            whereClause += " WHERE post LIKE '%"+keyword+"%'";
        }

        Integer totalData = ((BigInteger) entityManager.createNativeQuery("SELECT COUNT(*) FROM posts" + whereClause).getSingleResult()).intValue();
        PaginationMeta meta = getPaginationMeta(request.getPage(), totalData);

        PaginationLink links = getPaginationLink(meta, request.getUrl());

        final String postQuery = "SELECT posts.*," +
                " users.id AS user_id," +
                " users.name," +
                " users.email," +
                " (SELECT COUNT(*) FROM comments WHERE comments.post_id = posts.id) AS comment_count" +
                " FROM posts" +
                " INNER JOIN users ON users.id = posts.user_id" +
                whereClause +
                " ORDER BY created_at DESC" +
                " LIMIT " + meta.getPerPage().toString() +
                " OFFSET " + meta.offset().toString();

        Query query = entityManager.createNativeQuery(postQuery, "PostMapping");
        List<PostMapping> posts = query.getResultList();

        List<PostListResponse> responses = new ArrayList<>();
        posts.forEach(post -> responses.add(Response.convertPostToCollectionResponse(post)));

        entityManager.close();

        return new PaginationResponse<List<PostListResponse>>(200, "ok", responses, links, meta);
    }

    @Transactional
    public PostResponse createPost(PostRequest postRequest) {

        this.postRepository.createPost(
                postRequest.getPost(),
                postRequest.getUserId()
        );

        Integer postId = this.postRepository.getLastId().intValue();

        insertTags(postRequest.getTags(), postId);

        return findPostById(postId);

    }

    public PostResponse findPostById(Integer id) {
        Post post = findPostOrNotFound(id);
        return Response.convertPostToResponse(post);
    }

    @Transactional
    public PostResponse updatePost(Integer id, PostRequest postRequest) {

        this.postRepository.updatePostById(postRequest.getPost(), id);

        entityManager.createNativeQuery("DELETE FROM posts_tags WHERE posts_id = :postId")
                .setParameter("postId", id)
                .executeUpdate();

        insertTags(postRequest.getTags(), id);

        return findPostById(id);
    }

    public void deletePost(Integer id) {
        this.postRepository.deletePostById(id);
    }

    private Post findPostOrNotFound(Integer id) {

        List<Post> post = this.postRepository.findPostById(id);
        if(post.isEmpty()) {
            throw new NotFoundException("Data not found!");
        }
        return post.get(0);

    }

    public PostResponse createComment(Integer postId, CommentRequest commentRequest) {
        this.commentRepository.createComment(
                postId,
                commentRequest.getUserId(),
                commentRequest.getComment()
        );

        return findPostById(postId);
    }

    public PostResponse updateComment(Integer postId, Integer commentId, CommentRequest commentRequest) {
        this.commentRepository.updateComment(commentId, commentRequest.getComment());
        return findPostById(postId);
    }

    public PostResponse deleteComment(Integer postId, Integer commentId) {
        this.commentRepository.deleteComment(commentId);
        return findPostById(postId);
    }

    private PaginationMeta getPaginationMeta(Integer currentPage, Integer totalData) {
        Integer size = 10;
        Integer totalPage = (int) Math.ceil((double)totalData/(double)size);
        return new PaginationMeta(
                currentPage,
                totalData,
                size,
                totalPage
        );
    }

    private PaginationLink getPaginationLink(PaginationMeta paginationMeta, String baseUrl) {
        String nextUrl = baseUrl;
        if(paginationMeta.getCurrentPage() >= paginationMeta.getTotalPage()) {
            nextUrl = null;
        } else {
            Integer nextPage = paginationMeta.getCurrentPage()+1;
            nextUrl += "?page=" + nextPage.toString();
        }
        return new PaginationLink(nextUrl);
    }

    private void insertTags(List<Integer> tags, Integer postId) {
        tags.forEach(tag -> entityManager.createNativeQuery("INSERT INTO posts_tags(posts_id, tags_id) VALUE (:postId, :tagId)")
                .setParameter("postId", postId)
                .setParameter("tagId", tag)
                .executeUpdate()
        );

        entityManager.close();
    }

    public List<CustomResponse> getCustomPosts(String post, String name, String comment) {

        StringBuilder queryPost = new StringBuilder();
        queryPost.append("SELECT posts.* FROM posts INNER JOIN users ON users.id = posts.user_id");

        if(!post.isEmpty()){
            queryPost.append(" WHERE posts.post LIKE '%" + post + "%'");
        }

        if(!name.isEmpty()) {
            queryPost.append(" AND users.name LIKE '%"+ name +"%'");
        }

        if(!comment.isEmpty()) {
            queryPost.append(" AND EXISTS( SELECT comment FROM comments WHERE posts.id = comments.post_id AND comment LIKE '%"+ comment +"%' )");
        }

        Query query = entityManager.createNativeQuery(queryPost.toString(), Post.class);
        List<Post> posts = query.getResultList();

        List<CustomResponse> responses = new ArrayList<>();

        posts.forEach(val -> responses.add(Response.convertPostToCustomResponse(val)));

        return responses;

    }
}
