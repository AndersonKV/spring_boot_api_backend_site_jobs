package com.backend.demo.service.user;

import com.backend.demo.DTO.DeleteUserByIdDTO;
import com.backend.demo.exception.ApiRequestException;
import com.backend.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;


@Service
@AllArgsConstructor
public class UserDeleteService {
    private final String ID_NOT_FOUND = "id não encontrado";

    private final UserRepository userRepository;

    public ResponseEntity delete_by_id(  Long id) {
        try {
            var find = this.userRepository.findById(id).orElseThrow(() -> new IllegalStateException(ID_NOT_FOUND));

            this.userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (IllegalStateException e) {
            throw new ApiRequestException(e.getMessage());
        }
    }


    public ResponseEntity deleteAll() {
        this.userRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


}
