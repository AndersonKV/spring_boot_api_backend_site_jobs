package com.backend.demo.service.apply;

import com.backend.demo.exception.ApiRequestException;
import com.backend.demo.model.Apply;
import com.backend.demo.repository.ApplyRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class ApplyDeleteService {
    @Autowired
    private ApplyRepository applyRepository;

    public void deleteById(Long id) {
        try {
            this.applyRepository.deleteById(id);
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public void deleteByIdAndIdUserAndIdJob(Long id, Long id_user, Long id_job) {
        try {
            Optional<Apply> getApply = this.applyRepository.deleteByIdAndIdUserAndIdJob(id, id_user, id_job);

            if(getApply.isPresent()) {
                this.applyRepository.delete(getApply.get());
            } else {
                throw new ApiRequestException("nada foi encontrado");
            }
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public void destroyer() {
        try {
            this.applyRepository.deleteAll();
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
