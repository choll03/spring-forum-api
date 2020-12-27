package com.ismail.forum.repository;

import com.ismail.forum.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO users(name, email, created_at) VALUES(:name, :email, NOW())", nativeQuery = true)
    void register(
            @Param("name") String name,
            @Param("email") String email
    );

    @Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
    BigInteger getLastId();

}
