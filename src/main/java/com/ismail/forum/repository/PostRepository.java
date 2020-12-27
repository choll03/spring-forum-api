package com.ismail.forum.repository;

import com.ismail.forum.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(value = "SELECT * FROM posts WHERE id = :id", nativeQuery = true)
    List<Post> findPostById(@Param("id") Integer id);

    @Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
    BigInteger getLastId();

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO posts(post, user_id, created_at) VALUES(:post, :userId, NOW())", nativeQuery = true)
    void createPost(@Param("post") String post, @Param("userId") Integer userid);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM posts WHERE id = :id", nativeQuery = true)
    void deletePostById(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query(
            value = "UPDATE posts SET post = :post, updated_at = NOW() WHERE id = :id",
            nativeQuery = true)
    void updatePostById(@Param("post") String post, @Param("id") Integer id);

}
