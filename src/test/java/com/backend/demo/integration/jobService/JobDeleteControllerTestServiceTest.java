package com.backend.demo.integration.jobService;

import com.backend.demo.DTO.LoginDTO;
import com.backend.demo.enums.*;
import com.backend.demo.model.Job;
import com.backend.demo.model.User;
import com.backend.demo.repository.JobRepository;
import com.backend.demo.repository.UserRepository;
import com.backend.demo.service.auth.AuthService;
import com.backend.demo.service.job.JobCreateService;
import com.backend.demo.service.job.JobDeleteService;
import com.backend.demo.service.job.JobFindService;
import com.backend.demo.service.user.UserCreateService;
import com.backend.demo.service.user.UserDeleteService;
import com.backend.demo.utils.JobFactory;
import com.backend.demo.utils.UserFactory;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit4.SpringRunner;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
@DisplayName("TEST INTEGRATION - JobDeleteService")
public class JobDeleteControllerTestServiceTest {
    private final String EMAIL_NOT_FOUND = "esse email não foi registrado";

    @Autowired
    UserRepository userRepository;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    AuthService authService;

    @Autowired
    UserCreateService userCreateService;

    @Autowired
    JobFindService jobFindService;

    @Autowired
    JobDeleteService jobDeleteService;


    @Autowired
    JobCreateService jobCreateService;

    @MockBean
    User userShouldApply;

    @AfterEach
    void tearDown() {
        this.userRepository.deleteAll();
        this.jobRepository.deleteAll();
    }

    @Test
    @DisplayName("should delete job by id")
    public void shouldDeleteJobById() {
        var userCreated = new UserFactory().createWithRoleUser("exemplo1@gmail.com");

        this.userCreateService.create(userCreated);

        LoginDTO loginDTO = new LoginDTO();

        loginDTO.setEmail(userCreated.getEmail());
        loginDTO.setPassword("senhasenha");

        var sign_in = this.authService.signIn(loginDTO);

        var jobCreated = new JobFactory().create(sign_in.getBody().getId(), sign_in.getBody().getToken());

        var createdJob = this.jobRepository.save(jobCreated);

        var res = this.jobDeleteService.delete_by_id(createdJob.getId());

        Assertions.assertEquals(HttpStatus.ACCEPTED, res.getStatusCode());
    }

    @Test
    @DisplayName("should fail in delete job by id")
    public void shouldFailInDeleteJobById() {
        try {
            var res = this.jobDeleteService.delete_by_id(99l);

            Assertions.assertEquals(HttpStatus.ACCEPTED, res.getStatusCode());
        } catch (RuntimeException e) {
            Assertions.assertEquals("Id " + 99l + " não encontrado", e.getMessage());
        }
    }

    @Test
    @DisplayName("should delete all users")
    public void shouldDeleteAllUsers() {
            var res = this.jobDeleteService.destroyer();
            Assertions.assertEquals(HttpStatus.ACCEPTED, res.getStatusCode());

    }
}
