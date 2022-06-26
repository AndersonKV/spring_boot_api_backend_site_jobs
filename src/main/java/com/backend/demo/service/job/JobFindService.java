package com.backend.demo.service.job;

import com.backend.demo.DTO.DataJobSuccessDTO;
import com.backend.demo.model.Job;
import com.backend.demo.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobFindService {
    @Autowired
    private JobRepository jobRepository;


    public Job findById(Long id) {
        try {
            return jobRepository.findById(id).orElseThrow(() -> new IllegalStateException("id não foi encontrado"));
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e);
        }

    }

    public Job findByUserId(Long id) {
        try {
            return jobRepository.findByUserId(id).orElseThrow(() -> new IllegalStateException("id não foi encontrado"));
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e);
        }

    }

    public List<Job> findByTech(String tech) {

        try {
            //return this.jobRepository.searchByTechsLike(tech, type_contract, remote);
            return this.jobRepository.findByTech(tech);
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage());
        }

    }

    public DataJobSuccessDTO listJobs() {
        try {
            return new DataJobSuccessDTO(jobRepository.findAll(), true, HttpStatus.OK);
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e);
        }

    }

    public List<Job> findTheLastThreeJobs() {
        try {
            return jobRepository.findByOrderByIdDesc();
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e);
        }

    }

}
