package com.backend.demo.service.user;

import com.backend.demo.DTO.UserDTO;
import com.backend.demo.model.User;
import com.backend.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserFindService {
    private final UserRepository userRepository;

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("id " + id + " não encontrado")
        );

        UserDTO userDTO = new UserDTO();

        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setId(user.getId());
        userDTO.setCreated_at(user.getCreated_at());
        userDTO.setAvatar(user.getAvatar());
        return userDTO;
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

}
