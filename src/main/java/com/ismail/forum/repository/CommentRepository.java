package com.ismail.forum.repository;

import com.ismail.forum.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO comments(post_id, user_id, comment, created_at) VALUES(:postId, :userId, :comment, NOW())", nativeQuery = true)
    void createComment(
            @Param("postId") Integer postId,
            @Param("userId") Integer userId,
            @Param("comment") String comment
    );

    @Transactional
    @Modifying
    @Query(value = "UPDATE comments SET comment = :comment, updated_at = NOW() WHERE id = :id ", nativeQuery = true)
    void updateComment(
            @Param("id") Integer id,
            @Param("comment") String comment
    );

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM comments WHERE id = :id",nativeQuery = true)
    void deleteComment(@Param("id") Integer id);
}
