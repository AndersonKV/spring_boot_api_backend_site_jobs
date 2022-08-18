package com.backend.demo.service.matching;

import com.backend.demo.exception.ApiRequestException;
import com.backend.demo.model.Matching;
import com.backend.demo.repository.MatchingRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class MatchingDeleteService {
    @Autowired
    private MatchingRepository matchingRepository;

    public ResponseEntity deleteById(Long id) {
        try {
            Optional<Matching> find =  this.matchingRepository.findById(id);

            if (find.isEmpty()) {
                throw new ApiRequestException("id " + id + " não foi encontrado em nosso banco.");
            }

            this.matchingRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public ResponseEntity deleteByIdAndIdUser(Long id, Long id_user) {
        try {
            Optional<Matching> find =  this.matchingRepository.deleteByIdAndIdUser(id, id_user);

            if (find.isEmpty()) {
                throw new ApiRequestException("id " + id + " e id_user " + id_user + " não foi encontrado em nosso banco.");
            }

            this.matchingRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
    public ResponseEntity deleteByIdAndIdJob(Long id, Long id_job) {
        try {
            Optional<Matching> find =  this.matchingRepository.deleteByIdAndIdJob(id, id_job);

            if (find.isEmpty()) {
                throw new ApiRequestException("id " + id + " e id_job " + id_job + " não foi encontrado em nosso banco.");
            }

            this.matchingRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public ResponseEntity deleteByIdAndIdUserAndIdJob(Long id, Long id_user, Long id_job) {
        try {
            Optional<Matching> getApply = this.matchingRepository.deleteByIdAndIdUserAndIdJob(id, id_user, id_job);

            if (getApply.isEmpty()) {
                throw new ApiRequestException("id " + id_user + " não encontrado");
            }

            this.matchingRepository.delete(getApply.get());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public ResponseEntity destroyer() {
        try {
            this.matchingRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
