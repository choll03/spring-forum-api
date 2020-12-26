package com.ismail.forum.repository;

import com.ismail.forum.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    @Query(value = "SELECT * FROM tags ORDER BY created_at DESC", nativeQuery = true)
    List<Tag> getTags();
}
