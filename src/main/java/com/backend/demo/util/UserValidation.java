package com.backend.demo.util;

import com.backend.demo.enums.UserRole;
import com.backend.demo.exception.ApiRequestException;
import com.backend.demo.model.User;
import com.backend.demo.register.PasswordEncoded;
import com.backend.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserValidation {
    private final UserRepository userRepository;
    private final PasswordEncoded passwordEncoded;

    public void pass(User request) {
        Optional<User> emailExists = this.userRepository.findByEmail(request.getEmail());

        if (emailExists.isPresent()) {
            throw new ApiRequestException("email já foi registrado");
        }

        if (request.getName().trim().length() < 6 || request.getName().trim().length() > 30) {
            throw new ApiRequestException("O nome deve ter entre 6 e 30 caracteres");
        }

        if (request.getPassword().trim().length() <= 8) {
            throw new ApiRequestException("A senha deve ter pelo menos 8 digitos");
        }

        if (!request.getPassword().trim().equals(request.getConfirm_password().trim())) {
            throw new ApiRequestException("Senha de confirmação deve ser igual");
        }

        if (request.getRole() != UserRole.user && request.getRole() != UserRole.company) {
            throw new ApiRequestException("Necessário escolher entre user e company");
        }
    }

    public User update(User request, User user) {
        Optional<User> exist = this.userRepository.findByEmail(request.getEmail());

        if (!request.getEmail().equals(user.getEmail()) && exist.isPresent()) {
            throw new ApiRequestException("email já está em uso");
        }

        if (request.getName().trim().length() < 6 || request.getName().trim().length() > 30) {
            throw new ApiRequestException("O nome deve ter entre 6 e 30 caracteres");
        }

        if (request.getPassword().trim().length() <= 8) {
            throw new ApiRequestException("A senha deve ter pelo menos 8 digitos");
        }

        if (!request.getPassword().trim().equals(request.getConfirm_password().trim())) {
            throw new ApiRequestException("Senha de confirmação deve ser igual");
        }

        request.setId(user.getId());

        String encodedPassword = passwordEncoded.bCryptPasswordEncoder().encode(request.getPassword());

        request.setPassword(encodedPassword);
        request.setConfirm_password(encodedPassword);

        return request;
    }

}
