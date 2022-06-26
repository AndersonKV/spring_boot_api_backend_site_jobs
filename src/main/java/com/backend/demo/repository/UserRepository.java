package com.backend.demo.repository;

import com.backend.demo.model.Job;
import com.backend.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT user FROM User user WHERE user.email = ?1")
    Optional<User> findByEmail(String email);

    @Query("SELECT user FROM User user WHERE user.id = ?1")
    Optional<User> findById(Long id);

}
