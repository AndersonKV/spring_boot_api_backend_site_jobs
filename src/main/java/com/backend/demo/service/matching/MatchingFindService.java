package com.backend.demo.service.matching;

import com.backend.demo.exception.ApiRequestException;
import com.backend.demo.model.Matching;
import com.backend.demo.repository.MatchingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class MatchingFindService {

    @Autowired
    private MatchingRepository matchingRepository;

    public ResponseEntity<List<Matching>> findAll() {
        var find = this.matchingRepository.findAll();
        return new ResponseEntity<>(find, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<Matching> findById(Long id) {
        try {
            Optional<Matching> apply = this.matchingRepository.findById(id);

            if (!apply.isPresent()) {
                throw new ApiRequestException("id não encontrado");
            }

            return new ResponseEntity<>(apply.get(), HttpStatus.ACCEPTED);
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public ResponseEntity<Matching> findByIdUser(Long id) {
        try {
            Optional<Matching> apply = this.matchingRepository.findByIdUser(id);

            if (!apply.isPresent()) {
                throw new ApiRequestException("id não encontrado");
            }

            return new ResponseEntity<>(apply.get(), HttpStatus.ACCEPTED);
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage());
        }

    }

    public ResponseEntity<Matching> findByIdJob(Long id) {
        try {
            Optional<Matching> apply = this.matchingRepository.findByIdJob(id);

            if (!apply.isPresent()) {
                throw new ApiRequestException("id não encontrado");
            }

            return new ResponseEntity<>(apply.get(), HttpStatus.ACCEPTED);
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage());
        }

    }

    public ResponseEntity<Matching> findByIdAndUserIdAndIdJob(Long id, Long id_user, Long id_job) {
        try {
            Optional<Matching> apply = this.matchingRepository.findByIdAndIdUserAndIdJob(id, id_user, id_job);

            if (!apply.isPresent()) {
                throw new ApiRequestException("nenhuma informações foi encontrada com os ids: id " + id + ", id_user " + id_user + ", id_job " + id_job);
            }

            return new ResponseEntity<>(apply.get(), HttpStatus.ACCEPTED);
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
