package com.backend.demo.service.job;

import com.backend.demo.model.Job;
import com.backend.demo.repository.JobRepository;
import com.backend.demo.service.auth.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.backend.demo.util.JobValidation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;


@AllArgsConstructor
@Service
public class JobCreateService {
    private final JobRepository jobRepository;
    private final AuthService authService;
    private final JobValidation jobValidation;

    @Transactional
    public ResponseEntity create(Job request) {
        try {
             this.authService.signInWithToken(request.getToken());

            jobValidation.pass(request);

            this.jobRepository.save(request);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

}
