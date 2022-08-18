package com.backend.demo.service.user;

import com.backend.demo.DTO.UserDTO;
import com.backend.demo.model.User;
import com.backend.demo.repository.UserRepository;
import com.backend.demo.util.UserValidation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFindService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserValidation userValidation;

    public ResponseEntity<UserDTO> getUserById(Long id) {
        try {
            User user = this.userValidation.findById(id);


            UserDTO userDTO = new UserDTO(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getAvatar(),
                    user.getCreated_at()
            );

            return new ResponseEntity(userDTO, HttpStatus.ACCEPTED);
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public ResponseEntity<List<User>> list_users() {
        List<User> list = this.userRepository.findAll();
        //return ResponseEntity.ok(list);
        return new ResponseEntity(list, HttpStatus.ACCEPTED);
    }

}
