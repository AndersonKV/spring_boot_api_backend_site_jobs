package com.backend.demo.service.matching;

import com.backend.demo.model.Matching;
import com.backend.demo.repository.MatchingRepository;
import com.backend.demo.service.auth.AuthService;
import com.backend.demo.util.ApplyValidation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MatchingCreateService {
    private final MatchingRepository matchingRepository;
    private final ApplyValidation applyValidation;
    private final AuthService authService;

    public ResponseEntity  create(Matching request) {
        try {
            this.authService.signInWithToken(request.getToken());

            this.applyValidation.pass(request);
            this.matchingRepository.save(request);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage());
        }

    }
}
