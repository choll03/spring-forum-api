package com.ismail.forum.repository;

import com.ismail.forum.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    @Query(value = "SELECT * FROM tags ORDER BY created_at DESC", nativeQuery = true)
    List<Tag> getTags();

    @Modifying
    @Transactional
    @Query(value = "UPDATE tags SET name = :name, updated_at = NOW() WHERE id = :id", nativeQuery = true)
    void updateTagById(@Param("id") Integer id, @Param("name") String name);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM tags WHERE id = :id", nativeQuery = true)
    void deleteTagById(@Param("id") Integer id);
}
