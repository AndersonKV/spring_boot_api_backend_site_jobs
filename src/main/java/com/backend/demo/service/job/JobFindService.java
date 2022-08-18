package com.backend.demo.service.job;

import com.backend.demo.DTO.DataJobSuccessDTO;
import com.backend.demo.DTO.UserDTO;
import com.backend.demo.exception.ApiRequestException;
import com.backend.demo.model.Job;
import com.backend.demo.repository.JobRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JobFindService {
    @Autowired
    private JobRepository jobRepository;

    public ResponseEntity findById(Long id) {
        try {
            var find = jobRepository.findById(id);

            if (find.isEmpty()) {
                throw new ApiRequestException("id não foi encontrado");
            }
            return new ResponseEntity<>(find, HttpStatus.ACCEPTED);
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e);
        }

    }

    public ResponseEntity findByUserId(Long id) {
        try {
            var find = jobRepository.findByUserId(id);

            if (find.isEmpty()) {
                throw new ApiRequestException("id não foi encontrado");
            }
            return new ResponseEntity<>(find, HttpStatus.ACCEPTED);
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e);
        }

    }

    @Transactional
    public ResponseEntity<List<Job>> findByTech(String tech) {
        try {
            var find = this.jobRepository.findByTech(tech);
            return new ResponseEntity<>(find, HttpStatus.ACCEPTED);
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage());
        }

    }

    public ResponseEntity findAll() {
        try {
            var find = jobRepository.findAll();
            return new ResponseEntity<>(find, HttpStatus.ACCEPTED);
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e);
        }

    }

    public ResponseEntity<List<Job>> findTheLastThreeJobs() {
        try {
            var find = jobRepository.findByOrderByIdDesc();
            return new ResponseEntity<>(find, HttpStatus.ACCEPTED);
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e);
        }

    }

}
