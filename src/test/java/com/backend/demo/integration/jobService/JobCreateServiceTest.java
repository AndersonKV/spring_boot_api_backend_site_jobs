package com.backend.demo.integration.jobService;

import com.backend.demo.DTO.LoginDTO;
import com.backend.demo.enums.*;
import com.backend.demo.model.Job;
import com.backend.demo.model.User;
import com.backend.demo.repository.JobRepository;
import com.backend.demo.repository.UserRepository;
import com.backend.demo.service.auth.AuthService;
import com.backend.demo.service.job.JobCreateService;
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
@DisplayName("TEST INTEGRATION - JobCreateService")
public class JobCreateServiceTest {
    private final String WRONG_TOKEN = "aqwebGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwiaWF0IjoxNjYwNzQ2NzQwLCJzdWIiOiIkMmEkMTAkRFFYRmE5Si9BY0NFN1ZJd3M5TlRGZUxRZTh6MzBVeGFMdVZQTFVtWnJuZXE3Qmdrd2VPUEMiLCJpc3MiOiJhbmRlcnNvbiIsImV4cCI6MTY2MDc0NzM0MH0.jbWvvJTyKATbAHyIxu7Kju7De-8F-QJUj4iSkcyG9sw";
    private final String EMAIL_NOT_FOUND = "esse email não foi registrado";
    @Autowired
    UserRepository userRepository;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    AuthService authService;
    @Autowired
    UserDeleteService userDeleteService;
    @Autowired
    UserCreateService userCreateService;

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
    @DisplayName("should create job")
    public void shouldCreateJob() {
        var userCreated = new UserFactory().createUserRoleCompany("exempwwwlo1@gmail.com");

        this.userCreateService.create(userCreated);

        LoginDTO loginDTO = new LoginDTO();

        loginDTO.setEmail(userCreated.getEmail());
        loginDTO.setPassword("senhasenha");

        var sign_in = this.authService.signIn(loginDTO);

        var jobCreated = new JobFactory().create(sign_in.getBody().getId(), sign_in.getBody().getToken());

        var createdJob = this.jobRepository.save(jobCreated);

        var res = this.jobCreateService.create(createdJob);
        Assertions.assertEquals(HttpStatus.CREATED, res.getStatusCode());

    }



    @Test
    @DisplayName("should fai in create job with wrong role")
    public void shouldFailInCreateWithRoleWrong() {
        try {
            var userCreated = new UserFactory().createWithRoleUser("exemplowww1@gmail.com");

            this.userCreateService.create(userCreated);

            LoginDTO loginDTO = new LoginDTO();

            loginDTO.setEmail(userCreated.getEmail());
            loginDTO.setPassword("senhasenha");

            var sign_in = this.authService.signIn(loginDTO);

            var jobCreated = new JobFactory().create(sign_in.getBody().getId(), sign_in.getBody().getToken());

            this.jobCreateService.create(jobCreated);
        } catch (RuntimeException e) {
            Assertions.assertEquals( "você não tem autorização para postar", e.getMessage());
        }
    }

    @Test
    @DisplayName("should fai in create job with wrong token")
    public void shouldFailInCreateWithWrongToken() {
        try {
            var userCreated = new UserFactory().createUserRoleCompany("exemplowwwwww1@gmail.com");

            this.userCreateService.create(userCreated);

            LoginDTO loginDTO = new LoginDTO();

            loginDTO.setEmail(userCreated.getEmail());
            loginDTO.setPassword("senhasenha");

            var sign_in = this.authService.signIn(loginDTO);

            var jobCreated = new JobFactory().create(sign_in.getBody().getId(), "ads5ds4sa5sadsa5d1dsa65sda165asd1a65ds1d5sa6dsadsadsa");

            this.jobCreateService.create(jobCreated);
        } catch (RuntimeException e) {
         Assertions.assertTrue(e.getMessage().contains("JWT"));
         }
    }
}
