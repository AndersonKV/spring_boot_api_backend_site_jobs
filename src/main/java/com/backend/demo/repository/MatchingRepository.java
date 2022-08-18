package com.backend.demo.repository;

import com.backend.demo.model.Matching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchingRepository extends JpaRepository<Matching, Long> {

    @Query("SELECT a FROM Matching a WHERE a.id = ?1 and a.id_user = ?2 and a.id_job = ?3")
    Optional<Matching> findByIdAndIdUserAndIdJob(Long id, Long id_user, Long id_job);

    @Query("SELECT a FROM Matching a WHERE a.id = ?1 and a.id_user = ?2")
    Optional<Matching> deleteByIdAndIdUser(Long id, Long id_user);

    @Query("SELECT a FROM Matching a WHERE a.id = ?1 and a.id_job = ?2")
    Optional<Matching> deleteByIdAndIdJob(Long id, Long id_job);

    @Query("SELECT a FROM Matching a WHERE a.id_user = ?1 and a.id_job = ?2")
    Optional<Matching> verifyIfUserApply(Long id_user, Long id_job);

    @Query("SELECT a FROM Matching a WHERE a.id_user = ?1")
    Optional<Matching> findByIdUser(Long id_user  );

    @Query("SELECT a FROM Matching a WHERE a.id_job = ?1")
    Optional<Matching> findByIdJob(Long id_job  );

    @Query("SELECT a FROM Matching a WHERE a.id = ?1 and a.id_user = ?2 and a.id_job = ?3")
    Optional<Matching> deleteByIdAndIdUserAndIdJob(Long id, Long id_user, Long id_job);


 }
