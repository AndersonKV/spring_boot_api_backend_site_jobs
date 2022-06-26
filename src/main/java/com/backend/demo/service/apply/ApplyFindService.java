package com.backend.demo.service.apply;

import com.backend.demo.exception.ApiRequestException;
import com.backend.demo.model.Apply;
import com.backend.demo.repository.ApplyRepository;
import com.backend.demo.repository.JobRepository;
import com.backend.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ApplyFindService {

    @Autowired
    private ApplyRepository applyRepository;

    public List<Apply> findAll() {
        return this.applyRepository.findAll();
    }

    public Optional<Apply> findById(Long id) {
        try {
            Optional<Apply> apply = this.applyRepository.findById(id);

            if (!apply.isPresent()) {
                throw new ApiRequestException("id não encontrado");
            }

            return apply;
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public Optional<Apply> findByIdUser(Long id) {
        try {
            Optional<Apply> apply = this.applyRepository.findByIdUser(id);

            if (!apply.isPresent()) {
                throw new ApiRequestException("id não encontrado");
            }

            return apply;
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage());
        }

    }

    public  Optional<Apply>  findByIdJob(Long id) {
        try {
            Optional<Apply> apply = this.applyRepository.findByIdJob(id);

            if (!apply.isPresent()) {
                throw new ApiRequestException("id não encontrado");
            }

            return apply;
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage());
        }

    }

    public Optional<Apply> findByIdAndUserIdAndIdJob(Long id, Long id_user, Long id_job) {
        try {
            Optional<Apply> apply = this.applyRepository.findByIdAndIdUserAndIdJob(id, id_user, id_job);

            if (!apply.isPresent()) {
                throw new ApiRequestException("nenhuma informações foi encontrada com os ids: id " + id + ", id_user " + id_user + ", id_job " + id_job);
            }

            return apply;
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
