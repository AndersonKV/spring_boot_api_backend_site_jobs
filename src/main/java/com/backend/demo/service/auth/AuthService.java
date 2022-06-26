package com.backend.demo.service.auth;

import com.backend.demo.DTO.LoginDTO;
import com.backend.demo.auth.Auth;
import com.backend.demo.model.User;
import com.backend.demo.register.PasswordEncoded;
import com.backend.demo.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoded passwordEncoded;
    private final Auth auth;

    public LoginDTO signIn(LoginDTO request) {
        User getUser = this.userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new IllegalStateException("esse email não foi registrado")
        );

        boolean encodedPassword = passwordEncoded.bCryptPasswordEncoder().matches(request.getPassword(), getUser.getPassword());

        if (!encodedPassword) {
            throw new IllegalStateException("Senha errada");
        }

        LoginDTO loginDTO = new LoginDTO();

        loginDTO.setId(getUser.getId());
        loginDTO.setEmail(getUser.getEmail());
        loginDTO.setName(getUser.getName());

        loginDTO.setToken(auth.createJWT(
                getUser.getId().toString(),
                getUser.getName(),
                getUser.getPassword(),
                600000));

        return loginDTO;
    }

    public User signInWithToken(String token) {
        try {
            Claims verifyToken = auth.decodeJWT(token);
            long id = Long.valueOf(verifyToken.getId());
            return userRepository.findById(id).orElseThrow(() -> new IllegalStateException("falha ao autenticar com token, id " + id + " não encontrado"));
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage());
        }

    }
}
