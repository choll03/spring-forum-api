package com.ismail.forum.service;

import com.ismail.forum.entity.Post;
import com.ismail.forum.helper.Response;
import com.ismail.forum.model.*;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class PostService {

    @PersistenceContext
    private EntityManager entityManager;

    public PaginationResponse<List<PostListResponse>> getPosts(PaginationRequest request, String keyword) {

        // keyword
        String whereClause = "";
        if(!keyword.isEmpty()) {
            whereClause += " WHERE post LIKE '%"+keyword+"%'";
        }

        // setup pagination meta
        Integer currentPage = request.getPage();
        Integer size = 10;
        Integer offset = (currentPage - 1) * size;
        Integer totalData = ((BigInteger) entityManager.createNativeQuery("SELECT COUNT(*) FROM posts" + whereClause).getSingleResult()).intValue();
        Integer totalPage = (int) Math.ceil((double)totalData/(double)size);
        PaginationMeta meta = new PaginationMeta(currentPage, totalData, size, totalPage);

        // setup pagination links
        String nextUrl = request.getUrl();
        if(currentPage >= totalPage) {
            nextUrl = null;
        } else {
            Integer nextPage = currentPage+1;
            nextUrl += "?page=" + nextPage.toString();
        }
        PaginationLink links = new PaginationLink(nextUrl);

        // get posts
        String postQuery = "SELECT posts.* FROM posts"+ whereClause +" ORDER BY created_at DESC LIMIT " + size.toString() + " OFFSET " + offset.toString();
        Query query = entityManager.createNativeQuery(postQuery, Post.class);
        List<Post> posts = query.getResultList();

        // convert post to post collection response
        List<PostListResponse> responses = new ArrayList<>();
        posts.stream().forEach(post -> {
            responses.add(Response.convertPostToCollectionResponse(post));
        });

        entityManager.close();

        return new PaginationResponse<List<PostListResponse>>(
                200,
                "ok",
                responses,
                links,
                meta
        );
    }

    @Transactional
    public PostResponse createPost(PostRequest postRequest) {

        // table posts
        Query q = entityManager.createNativeQuery("INSERT INTO posts (post, user_id, created_at, updated_at) VALUES (:post, :user_id, :created_at, :updated_at)", Post.class);
        q.setParameter("post", postRequest.getPost())
                .setParameter("user_id", postRequest.getUserId())
                .setParameter("created_at", new Date())
                .setParameter("updated_at", new Date())
                .executeUpdate();
        // get last post ID
        Integer postId = ((BigInteger) entityManager.createNativeQuery("SELECT LAST_INSERT_ID()").getSingleResult()).intValue();

        // insert table pivot (posts_tags)
        postRequest.getTags().forEach(tag -> {
            entityManager.createNativeQuery("INSERT INTO posts_tags(posts_id, tags_id) VALUE (:post_id, :tag_id)")
                    .setParameter("post_id", postId)
                    .setParameter("tag_id", tag)
                    .executeUpdate();
        });

        entityManager.close();

        return findPostById(postId);

    }

    public PostResponse findPostById(Integer id) {
        Post post = findPostOrNotFound(id);
        return Response.convertPostToResponse(post);
    }

    @Transactional
    public PostResponse updatePost(Integer id, PostRequest postRequest) {
        Query q = entityManager.createNativeQuery("UPDATE posts SET post=:post, updated_at = :updated_at WHERE id = :id", Post.class);
        q.setParameter("post", postRequest.getPost())
                .setParameter("updated_at", new Date())
                .setParameter("id", id)
                .executeUpdate();

        entityManager.createNativeQuery("DELETE FROM posts_tags WHERE posts_id = :post_id")
            .setParameter("post_id", id)
            .executeUpdate();

        postRequest.getTags().forEach(tag -> {
            entityManager.createNativeQuery("INSERT INTO posts_tags(posts_id, tags_id) VALUE (:post_id, :tag_id)")
                    .setParameter("post_id", id)
                    .setParameter("tag_id", tag)
                    .executeUpdate();
        });

        return findPostById(id);
    }

    @Transactional
    public void deletePost(Integer id) {
        entityManager.createNativeQuery("DELETE FROM posts WHERE id=:id")
                .setParameter("id", id)
                .executeUpdate();

        entityManager.close();
    }

    private Post findPostOrNotFound(Integer id) {
        String postQuery = "SELECT * " +
                "FROM posts " +
                "WHERE id = :id";

        Query query = entityManager.createNativeQuery(postQuery, Post.class);
        query.setParameter("id", id);
        Post post = (Post) query.getSingleResult();

        entityManager.close();

        return post;

    }


    @Transactional
    public PostResponse createComment(Integer postId, CommentRequest commentRequest) {
        Query query = entityManager.createNativeQuery("INSERT into comments(post_id, user_id, comment, created_at) VALUE (:postId, :userId, :comment, :createdAt)");
        query.setParameter("postId", postId)
                .setParameter("userId", commentRequest.getUserId())
                .setParameter("comment", commentRequest.getComment())
                .setParameter("createdAt", new Date())
                .executeUpdate();

        entityManager.close();

        return findPostById(postId);
    }

    @Transactional
    public PostResponse updateComment(Integer postId, Integer commentId, CommentRequest commentRequest) {
        Query query = entityManager.createNativeQuery("UPDATE comments SET comment= :comment, updated_at= :updatedAt WHERE id = :id");
        query.setParameter("comment", commentRequest.getComment())
                .setParameter("updatedAt", new Date())
                .setParameter("id", commentId)
                .executeUpdate();

        entityManager.close();
        return findPostById(postId);
    }

    @Transactional
    public PostResponse deleteComment(Integer postId, Integer commentId) {
        Query query = entityManager.createNativeQuery("DELETE FROM comments WHERE id = :id");
        query.setParameter("id", commentId)
                .executeUpdate();

        return findPostById(postId);
    }
}
