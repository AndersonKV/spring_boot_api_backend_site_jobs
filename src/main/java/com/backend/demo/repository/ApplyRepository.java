package com.backend.demo.repository;

import com.backend.demo.model.Apply;
import com.backend.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplyRepository extends JpaRepository<Apply, Long> {

    @Query("SELECT a FROM Apply a WHERE a.id = ?1 and a.id_user = ?2 and a.id_job = ?3")
    Optional<Apply> findByIdAndIdUserAndIdJob(Long id, Long id_user, Long id_job);

    @Query("SELECT a FROM Apply a WHERE a.id_user = ?1 and a.id_job = ?2")
    Optional<Apply> verifyIfUserApply(Long id_user, Long id_job);

    @Query("SELECT a FROM Apply a WHERE a.id_user = ?1")
    Optional<Apply> findByIdUser(Long id_user  );

    @Query("SELECT a FROM Apply a WHERE a.id_job = ?1")
    Optional<Apply> findByIdJob(Long id_job  );

    @Query("SELECT a FROM Apply a WHERE a.id = ?1 and a.id_user = ?2 and a.id_job = ?3")
    Optional<Apply> deleteByIdAndIdUserAndIdJob(Long id, Long id_user, Long id_job);


 }
