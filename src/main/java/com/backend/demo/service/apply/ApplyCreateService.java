package com.backend.demo.service.apply;

import com.backend.demo.model.Apply;
import com.backend.demo.repository.ApplyRepository;
import com.backend.demo.repository.JobRepository;
import com.backend.demo.repository.UserRepository;
import com.backend.demo.service.auth.AuthService;
import com.backend.demo.util.ApplyValidation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ApplyCreateService {
    private final ApplyRepository applyRepository;
    private final ApplyValidation applyValidation;
    private final AuthService authService;

    public ResponseEntity<Object> create(Apply request) {
        try {
            this.authService.signInWithToken(request.getToken());

            this.applyValidation.pass(request);
            this.applyRepository.save(request);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage());
        }

    }
}
