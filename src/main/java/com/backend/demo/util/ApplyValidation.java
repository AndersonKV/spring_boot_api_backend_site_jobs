package com.backend.demo.util;

import com.backend.demo.enums.UserRole;
import com.backend.demo.exception.ApiRequestException;
import com.backend.demo.model.Apply;
import com.backend.demo.model.Job;
import com.backend.demo.model.User;
import com.backend.demo.repository.ApplyRepository;
import com.backend.demo.repository.JobRepository;
import com.backend.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ApplyValidation {
    final private UserRepository userRepository;
    final private JobRepository jobRepository;
    final private ApplyRepository applyRepository;
    final private DateValidation dateValidation;

    public void pass(Apply request) {
        Optional<User> user = this.userRepository.findById(request.getId_user());

        if(!user.isPresent()) {
            throw new ApiRequestException("id não encontrado");
        }

        if(user.get().getRole() == UserRole.company) {
            throw new ApiRequestException("você não tem autorização para aplicar");
        }

        Job job = this.jobRepository.findById(request.getId_job()).orElseThrow(() -> new IllegalStateException("vaga não foi encontrada, id:" + request.getId_job()));

        this.dateValidation.pass(job.getCreated_at());

        Optional<Apply> verify = this.applyRepository.verifyIfUserApply(
                request.getId_user(),
                request.getId_job());

        if (verify.isPresent()) {
            throw new ApiRequestException("Você já aplicou a essa vaga");
        }

    }
}
