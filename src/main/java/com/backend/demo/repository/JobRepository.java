package com.backend.demo.repository;

import com.backend.demo.DTO.FindTechDTO;
import com.backend.demo.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    @Query("SELECT job FROM Job job WHERE job.user_id = ?1")
    Optional<Job> findByUserId(Long id);

    @Query(value = "SELECT * FROM spring_job ORDER BY id DESC limit 3", nativeQuery = true)
    List<Job> findByOrderByIdDesc();

    @Query(value = "SELECT * FROM spring_job WHERE techs LIKE %:tech%", nativeQuery = true)
     List<Job> findByTech(String tech);

//    @Query(value = "SELECT * spring_boot_jobs_vaga where"+
//            "CASE" +
//                ":tech not null THEN techs LIKE %:tech% " +
//            "END", nativeQuery = true)
//    List<Job> searchByTechsLike(String tech, String type_contract, String remote);

}
