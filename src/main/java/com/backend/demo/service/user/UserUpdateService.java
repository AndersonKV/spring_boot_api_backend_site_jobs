package com.backend.demo.service.user;

import com.backend.demo.DTO.UserUpdateDTO;
import com.backend.demo.model.User;
import com.backend.demo.repository.UserRepository;
import com.backend.demo.service.auth.AuthService;
import com.backend.demo.util.UserValidation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserUpdateService {
    private final AuthService authService;
    private final UserRepository userRepository;
    private final UserValidation userValidation;

    public ResponseEntity<User> update(UserUpdateDTO request) {
        try {
            ResponseEntity<User> auth = this.authService.signInWithToken(request.getToken());

            var userUpdated = this.userValidation.update(request, auth.getBody());

            this.userRepository.save(userUpdated);

            return new ResponseEntity<>(userUpdated, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
