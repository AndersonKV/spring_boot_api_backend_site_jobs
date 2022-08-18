package com.backend.demo.service.auth;

import com.backend.demo.DTO.LoginDTO;
import com.backend.demo.auth.Auth;
import com.backend.demo.exception.ApiRequestException;
import com.backend.demo.model.Job;
import com.backend.demo.model.User;
import com.backend.demo.util.PasswordEncoded;
import com.backend.demo.repository.UserRepository;
import com.backend.demo.util.UserValidation;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoded passwordEncoded;
    private final Auth auth;
    private final String EMAIL_NOT_FOUND = "esse email não foi registrado";
    private final UserValidation userValidation;

    public ResponseEntity<LoginDTO> signIn(LoginDTO request) {
        var logged = this.userValidation.singIn(request);

        var token = auth.createJWT(
                logged.getId().toString(),
                logged.getName(),
                logged.getPassword(),
                600000);

        LoginDTO loginDTO = new LoginDTO(
                logged.getId(),
                logged.getName(),
                logged.getEmail(),
                logged.getPassword(),
                token);

        return new ResponseEntity<>(loginDTO, HttpStatus.ACCEPTED);
    }

    public ResponseEntity signInWithToken(String token) {
        try {
            Claims verifyToken = auth.decodeJWT(token);

            long id = Long.valueOf(verifyToken.getId());

            var find = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("id do usuário não encontrado"));

            return new ResponseEntity<>(find, HttpStatus.ACCEPTED);
        } catch (IllegalStateException e) {
            throw new ApiRequestException(e.getMessage());
        }
    }
}
