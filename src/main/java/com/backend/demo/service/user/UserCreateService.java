package com.backend.demo.service.user;

import com.backend.demo.model.User;
import com.backend.demo.util.PasswordEncoded;
import com.backend.demo.repository.UserRepository;
import com.backend.demo.util.UserValidation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserCreateService {
    private final UserRepository userRepository;
    private final PasswordEncoded passwordEncoded;
    private final UserValidation userValidation;

    public ResponseEntity create(User request) {
        this.userValidation.pass(request);

        String encodedPassword = passwordEncoded.bCryptPasswordEncoder().encode(request.getPassword());

        request.setPassword(encodedPassword);
        request.setConfirm_password(encodedPassword);

        userRepository.save(request);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
