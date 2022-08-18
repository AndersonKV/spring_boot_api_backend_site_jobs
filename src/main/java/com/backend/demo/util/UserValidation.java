package com.backend.demo.util;

import com.backend.demo.DTO.LoginDTO;
import com.backend.demo.DTO.UserUpdateDTO;
import com.backend.demo.enums.UserRole;
import com.backend.demo.exception.ApiRequestException;
import com.backend.demo.model.User;
import com.backend.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserValidation {
    private final UserRepository userRepository;
    private final PasswordEncoded passwordEncoded;
    private final String EMAIL_NOT_FOUND = "esse email não foi registrado";

    public void pass(User request) {
        Optional<User> emailExists = this.userRepository.findByEmail(request.getEmail());

        if (emailExists.isPresent()) {
            throw new ApiRequestException("email já foi registrado");
        }

        if (request.getName().trim().length() < 6) {
            throw new ApiRequestException("O nome deve ter pelo menos 6 caracteres");
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

    public User update(UserUpdateDTO userUpdate,  User userAuth) {
        Optional<User> exist = this.userRepository.findByEmail(userUpdate.getEmail());

        //true is equal
        var updateEmailIsEqualOlderEmail = userUpdate.getEmail().equals(userAuth.getEmail());
       //new email is exist return true
        var emailExist = exist.isPresent();

        if (!updateEmailIsEqualOlderEmail && emailExist) {
            throw new ApiRequestException("email já está em uso");
        }

        if (!userUpdate.getId().equals(userAuth.getId())) {
            throw new ApiRequestException("parece ter ocorrido um problema com id diferente");
        }

        if (userUpdate.getName().trim().length() < 6) {
            throw new ApiRequestException("O nome deve ter pelo menos 6 caracteres");
        }

        if (userUpdate.getPassword().trim().length() <= 8) {
            throw new ApiRequestException("A senha deve ter pelo menos 8 digitos");
        }

        if (!userUpdate.getPassword().trim().equals(userUpdate.getConfirm_password().trim())) {
            throw new ApiRequestException("Senha de confirmação deve ser igual");
        }

        userUpdate.setId(userAuth.getId());

        String encodedPassword = passwordEncoded.bCryptPasswordEncoder().encode(userUpdate.getPassword());

        userUpdate.setPassword(encodedPassword);
        userUpdate.setConfirm_password(encodedPassword);



        return  new User(userUpdate);
    }

    public User findById(Long id) {
        Optional<User> user = this.userRepository.findById(id);

        if (user.isEmpty()) {
            throw new ApiRequestException("id não encontrado");
        }

        return user.get();

    }

    public User singIn(LoginDTO request) {
        var findByEmail = this.userRepository.findByEmail(request.getEmail());

        if (findByEmail.isEmpty()) {
            throw new ApiRequestException(EMAIL_NOT_FOUND);
        }

        boolean encodedPassword = passwordEncoded.bCryptPasswordEncoder().matches(request.getPassword(), findByEmail.get().getPassword());

        if (!encodedPassword) {
            throw new ApiRequestException("Senha errada");
        }

        return findByEmail.get();

    }
}
