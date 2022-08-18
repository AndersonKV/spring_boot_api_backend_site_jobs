package com.backend.demo.integration.matchingService;

import com.backend.demo.DTO.LoginDTO;
import com.backend.demo.repository.MatchingRepository;
import com.backend.demo.repository.JobRepository;
import com.backend.demo.repository.UserRepository;
import com.backend.demo.service.matching.MatchingCreateService;
import com.backend.demo.service.auth.AuthService;
import com.backend.demo.service.job.JobCreateService;
import com.backend.demo.service.user.UserCreateService;
import com.backend.demo.service.user.UserDeleteService;
import com.backend.demo.utils.ApplyFactory;
import com.backend.demo.utils.JobFactory;
import com.backend.demo.utils.UserFactory;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@DisplayName("TEST INTEGRATION - MatchingCreateService")
public class MatchingCreateServiceTest {
    private final String EMAIL_NOT_FOUND = "esse email não foi registrado";
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthService authService;

    @Autowired
    UserDeleteService userDeleteService;

    @Autowired
    UserCreateService userCreateService;

    @Autowired
    JobCreateService jobCreateService;

    @Autowired
    MatchingCreateService matchingCreateService;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    MatchingRepository matchingRepository;


    @AfterEach
    void tearDown() {

        this.userRepository.deleteAll();
        this.jobRepository.deleteAll();
        this.matchingRepository.deleteAll();
    }


    @Test
    @DisplayName("should matching")
    public final void shouldMatching() {
        var userFactory = new UserFactory().createWithRoleUser("exemplwwwo1@gmail.com");

        this.userCreateService.create(userFactory);

        LoginDTO loginDTO = new LoginDTO();

        loginDTO.setEmail(userFactory.getEmail());
        loginDTO.setPassword("senhasenha");

        var sign_in = this.authService.signIn(loginDTO);

        var jobCreated = new JobFactory().create(sign_in.getBody().getId(), sign_in.getBody().getToken());

        var job = this.jobRepository.save(jobCreated);

        var apply = new ApplyFactory().create(sign_in.getBody().getId(), job.getId(), sign_in.getBody().getToken());

        var res = this.matchingCreateService.create(apply);

        Assertions.assertEquals(HttpStatus.CREATED, res.getStatusCode());
    }

    @Test
    @DisplayName("should fail in apply in job who matching")
    public final void shouldFailInMatchingInJob() {
        try {
            var userFactory = new UserFactory().createWithRoleUser("exewmplwwwo1@gmail.com");

            this.userCreateService.create(userFactory);

            LoginDTO loginDTO = new LoginDTO();

            loginDTO.setEmail(userFactory.getEmail());
            loginDTO.setPassword("senhasenha");

            var sign_in = this.authService.signIn(loginDTO);

            var jobCreated = new JobFactory().create(sign_in.getBody().getId(), sign_in.getBody().getToken());

            var job = this.jobRepository.save(jobCreated);

            var apply = new ApplyFactory().create(sign_in.getBody().getId(), job.getId(), sign_in.getBody().getToken());

            this.matchingCreateService.create(apply);
            this.matchingCreateService.create(apply);

        } catch (RuntimeException e) {
            Assertions.assertEquals("Você já aplicou a essa vaga", e.getMessage());
        }
    }

    @Test
    @DisplayName("should fail in matching with wrong token")
    public final void shouldFailInMatchingWithWrongToken() {
        try {
            var userFactory = new UserFactory()
                    .createWithRoleUser("exewmpwwlwwwo1@gmail.com");

            this.userCreateService.create(userFactory);

            LoginDTO loginDTO = new LoginDTO();

            loginDTO.setEmail(userFactory.getEmail());
            loginDTO.setPassword("senhasenha");

            var sign_in = this.authService.signIn(loginDTO);

            var jobCreated = new JobFactory().create(sign_in.getBody().getId(), sign_in.getBody().getToken() + "aa");

            var job = this.jobRepository.save(jobCreated);

            var apply = new ApplyFactory().create(sign_in.getBody().getId(), job.getId(), sign_in.getBody().getToken());

            this.matchingCreateService.create(apply);

        } catch (RuntimeException e) {
            Assertions.assertTrue(e.getMessage().contains("JWT"));
        }
    }

    @Test
    @DisplayName("should fail in matching with wrong id_job")
    public final void shouldFailInMatchingWithWrongIdJob() {
        try {
            var userFactory = new UserFactory().createWithRoleUser("exewwmplsswwwo1@gmail.com");

            this.userCreateService.create(userFactory);

            LoginDTO loginDTO = new LoginDTO();

            loginDTO.setEmail(userFactory.getEmail());
            loginDTO.setPassword("senhasenha");

            var sign_in = this.authService.signIn(loginDTO);

            var jobCreated = new JobFactory().create(sign_in.getBody().getId(), sign_in.getBody().getToken());

            var apply = new ApplyFactory().create(sign_in.getBody().getId(), 99l, sign_in.getBody().getToken());

            this.matchingCreateService.create(apply);
        } catch (RuntimeException e) {
            Assertions.assertEquals("id 99 não foi encontrado", e.getMessage());
        }
    }
    @Test
    @DisplayName("should fail in matching with wrong id_user")
    public final void shouldFailInMatchingWithWrongIdUser() {
        try {
            var userFactory = new UserFactory().createWithRoleUser("e3xewwmplsswwwo1@gmail.com");

            this.userCreateService.create(userFactory);

            LoginDTO loginDTO = new LoginDTO();

            loginDTO.setEmail(userFactory.getEmail());
            loginDTO.setPassword("senhasenha");

            var sign_in = this.authService.signIn(loginDTO);

            var jobCreated = new JobFactory().create(sign_in.getBody().getId(), sign_in.getBody().getToken());

            var apply = new ApplyFactory().create(99l, jobCreated.getId(), sign_in.getBody().getToken());

            this.matchingCreateService.create(apply);
        } catch (RuntimeException e) {
            Assertions.assertEquals("id 99 não foi encontrado", e.getMessage());
        }
    }

    @Test
    @DisplayName("should fail in matching with role company")
    public final void shouldFailInMatchingWithRoleCompany() {
        try {
            var userFactory = new UserFactory().createWithRoleUser("exemplsswwwo1@gmail.com");

            this.userCreateService.create(userFactory);

            LoginDTO loginDTO = new LoginDTO();

            loginDTO.setEmail(userFactory.getEmail());
            loginDTO.setPassword("senhasenha");

            var sign_in = this.authService.signIn(loginDTO);

            var jobCreated = new JobFactory().create(sign_in.getBody().getId(), sign_in.getBody().getToken());

            var job = this.jobRepository.save(jobCreated);

            var apply = new ApplyFactory().create(sign_in.getBody().getId(), job.getId(), sign_in.getBody().getToken());

            this.matchingCreateService.create(apply);
        } catch (RuntimeException e) {
            Assertions.assertEquals("você não tem autorização para aplicar", e.getMessage());
        }
    }

}
