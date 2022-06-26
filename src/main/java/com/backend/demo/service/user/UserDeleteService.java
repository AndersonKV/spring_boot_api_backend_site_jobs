package com.backend.demo.service.user;

import com.backend.demo.exception.ApiRequestException;
import com.backend.demo.model.User;
import com.backend.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDeleteService {
    @Autowired
    private UserRepository userRepository;

    public void deleteUserById(Long id) {
        Optional<User> userId = userRepository.findById(id);

        if (userId.isPresent()) {
            userRepository.deleteById(id);
        } else {
           throw new ApiRequestException("id " + id + " não encontrado");
        }

    }

    public void destroyer() {
        userRepository.deleteAll();
    }
}
