package com.backend.demo.service.job;

import com.backend.demo.exception.ApiRequestException;
import com.backend.demo.model.Job;
import com.backend.demo.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobDeleteService {
    @Autowired
    private JobRepository jobRepository;


    public void delete_by_id(Long id) {
        Optional<Job> userId = this.jobRepository.findById(id);

        if (!userId.isPresent()) {
            throw new ApiRequestException("Id " + id + " não encontrado");
        }

        this.jobRepository.deleteById(id);
    }

    public void destroyer() {
        this.jobRepository.deleteAll();
    }
}
