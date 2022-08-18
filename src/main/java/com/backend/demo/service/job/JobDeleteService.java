package com.backend.demo.service.job;

import com.backend.demo.exception.ApiRequestException;
import com.backend.demo.model.Job;
import com.backend.demo.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobDeleteService {
    @Autowired
    private JobRepository jobRepository;


    public ResponseEntity delete_by_id(Long id) {
        Optional<Job> userId = this.jobRepository.findById(id);

        if (!userId.isPresent()) {
            throw new ApiRequestException("Id " + id + " não encontrado");
        }

        this.jobRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    public ResponseEntity  destroyer() {
        this.jobRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
