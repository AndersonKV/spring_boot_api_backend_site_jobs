package com.backend.demo.util;

import com.backend.demo.enums.UserRole;
import com.backend.demo.exception.ApiRequestException;
import com.backend.demo.model.Matching;
import com.backend.demo.model.User;
import com.backend.demo.repository.MatchingRepository;
import com.backend.demo.repository.JobRepository;
import com.backend.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ApplyValidation {
    final private UserRepository userRepository;
    final private JobRepository jobRepository;
    final private MatchingRepository matchingRepository;
    final private DateValidation dateValidation;

    public void pass(Matching request) {
        Optional<User> user = this.userRepository.findById(request.getId_user());

        if (!user.isPresent()) {
            throw new ApiRequestException("id " + request.getId_user() + " não foi encontrado");
        }

        if (user.get().getRole() == UserRole.company) {
            throw new ApiRequestException("você não tem autorização para aplicar");
        }

        var job = this.jobRepository.findById(request.getId_job());

        if (job.isEmpty()) {
            throw new ApiRequestException("id " + request.getId_job() + " não foi encontrado");
        }

        // .orElseThrow(() -> new IllegalStateException("vaga não foi encontrada, id:" + request.getId_job()));

        this.dateValidation.pass(job.get().getCreated_at());

        Optional<Matching> verify = this.matchingRepository.verifyIfUserApply(
                request.getId_user(),
                request.getId_job());

        if (verify.isPresent()) {
            throw new ApiRequestException("Você já aplicou a essa vaga");
        }

    }
}
