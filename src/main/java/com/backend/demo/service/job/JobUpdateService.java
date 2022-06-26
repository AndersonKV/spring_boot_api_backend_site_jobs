package com.backend.demo.service.job;

import com.backend.demo.model.Job;
import com.backend.demo.model.User;
import com.backend.demo.repository.JobRepository;
import com.backend.demo.service.auth.AuthService;
import com.backend.demo.util.JobValidation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JobUpdateService {
    private final AuthService authService;
    private final JobValidation jobValidation;
    private final JobRepository jobRepository;

    public void update(Job request) {
        try {
            User userAuth = this.authService.signInWithToken(request.getToken());
            Job jobUpdate = this.jobValidation.update(request, userAuth);
            this.jobRepository.save(jobUpdate);
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

}
