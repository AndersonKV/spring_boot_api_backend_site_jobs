package com.backend.demo.integration.jobService;

import com.backend.demo.DTO.LoginDTO;
import com.backend.demo.enums.*;
import com.backend.demo.model.Job;
import com.backend.demo.model.User;
import com.backend.demo.repository.JobRepository;
import com.backend.demo.repository.UserRepository;
import com.backend.demo.service.auth.AuthService;
import com.backend.demo.service.job.JobCreateService;
import com.backend.demo.service.job.JobFindService;
import com.backend.demo.service.user.UserCreateService;
import com.backend.demo.utils.JobFactory;
import com.backend.demo.utils.UserFactory;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.GreaterOrEqual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;


import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
@DisplayName("TEST INTEGRATION - JobFindService")
public class JobFindServiceTest {
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
    JobCreateService jobCreateService;

    @MockBean
    User userShouldApply;

    @AfterEach
    void tearDown() {
        this.userRepository.deleteAll();
        this.jobRepository.deleteAll();
    }

    @Test
    @DisplayName("should find job")
    public void shouldFindJob() {
        var userCreated = new UserFactory().createUserRoleCompany("exemplo331@gmail.com");

        this.userCreateService.create(userCreated);

        LoginDTO loginDTO = new LoginDTO();

        loginDTO.setEmail(userCreated.getEmail());
        loginDTO.setPassword("senhasenha");

        var sign_in = this.authService.signIn(loginDTO);

        var jobCreated = new JobFactory().create(sign_in.getBody().getId(), sign_in.getBody().getToken());

        var createdJob = this.jobRepository.save(jobCreated);

        var res = this.jobFindService.findById(createdJob.getId());

        Assertions.assertEquals(HttpStatus.ACCEPTED, res.getStatusCode());
        Assertions.assertEquals(jobCreated.getUser_id(), sign_in.getBody().getId());
    }

    @Test
    @DisplayName("should fail find job")
    public void shouldFailInFindJob() {
        try {
            this.jobFindService.findById(99l);
        } catch (RuntimeException e) {
            Assertions.assertTrue(e.getMessage().contains("id não foi encontrado"));
        }
    }

    @Test
    @DisplayName("should find all jobs")
    public void shouldFindAllJobs() {
        var res = this.jobFindService.findAll();
        Assertions.assertEquals(HttpStatus.ACCEPTED, res.getStatusCode());
    }

    @Test
    @DisplayName("should find by tech")
    public void shouldFindByTech() {
        var userCreated = new UserFactory().createUserRoleCompany("exemplo2@gmail.com");

        this.userCreateService.create(userCreated);

        LoginDTO loginDTO = new LoginDTO();

        loginDTO.setEmail(userCreated.getEmail());
        loginDTO.setPassword("senhasenha");

        var sign_in = this.authService.signIn(loginDTO);

        var jobCreated = new JobFactory().create(sign_in.getBody().getId(), sign_in.getBody().getToken());

         this.jobRepository.save(jobCreated);


        var getTech = this.jobFindService.findByTech("ruby");

        Assertions.assertEquals(HttpStatus.ACCEPTED, getTech.getStatusCode());
        Assertions.assertTrue(getTech.getBody().size() >= 1);

    }

    @Test
    @DisplayName("should find the last three jobs")
    public void shouldFindTheLastThreeJobs() {

        var userCreated = new UserFactory().createUserRoleCompany("exemplo4@gmail.com");

        this.userCreateService.create(userCreated);

        LoginDTO loginDTO = new LoginDTO();

        loginDTO.setEmail(userCreated.getEmail());
        loginDTO.setPassword("senhasenha");

        var sign_in = this.authService.signIn(loginDTO);

        var jobCreated = new JobFactory().create(sign_in.getBody().getId(), sign_in.getBody().getToken());
        var jobCreated2 = new JobFactory().create(sign_in.getBody().getId(), sign_in.getBody().getToken());
        var jobCreated3 = new JobFactory().create(sign_in.getBody().getId(), sign_in.getBody().getToken());

        this.jobRepository.save(jobCreated);
        this.jobRepository.save(jobCreated2);
        this.jobRepository.save(jobCreated3);

        var getLastThreeJobs = this.jobFindService.findTheLastThreeJobs();

        Assertions.assertEquals(getLastThreeJobs.getStatusCode(), HttpStatus.ACCEPTED);
        Assertions.assertEquals( 3, getLastThreeJobs.getBody().size());

    }


}
