package com.backend.demo.integration.matchingService;


import com.backend.demo.DTO.LoginDTO;
import com.backend.demo.model.Matching;
import com.backend.demo.repository.MatchingRepository;
import com.backend.demo.repository.JobRepository;
import com.backend.demo.repository.UserRepository;
import com.backend.demo.service.matching.MatchingCreateService;
import com.backend.demo.service.matching.MatchingDeleteService;
import com.backend.demo.service.matching.MatchingFindService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@DisplayName("TEST INTEGRATION - MatchingDeleteService")
public class MatchingDeleteServiceTest {

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
    MatchingFindService matchingFindService;


    @Autowired
    MatchingDeleteService matchingDeleteService;

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
    @DisplayName("should delete by id")
    public final void shouldDeleteById() {
        var userFactory = new UserFactory().createWithRoleUser("6d6666@gmail.com");

        this.userCreateService.create(userFactory);

        LoginDTO loginDTO = new LoginDTO();

        loginDTO.setEmail(userFactory.getEmail());
        loginDTO.setPassword("senhasenha");

        var sign_in = this.authService.signIn(loginDTO);

        var jobCreated = new JobFactory().create(sign_in.getBody().getId(), sign_in.getBody().getToken());

        var job = this.jobRepository.save(jobCreated);

        var apply = new ApplyFactory().create(sign_in.getBody().getId(), job.getId(), sign_in.getBody().getToken());

        var applyCreated = this.matchingRepository.save(apply);

        ResponseEntity<Matching> res = this.matchingDeleteService.deleteById(applyCreated.getId());

        var findById = this.matchingRepository.findById(applyCreated.getId());

        Assertions.assertEquals(HttpStatus.ACCEPTED, res.getStatusCode());
    }

    @Test
    @DisplayName("should fail in delete by id")
    public final void shouldFailInDeleteById() {
        try {
            this.matchingDeleteService.deleteById(99l);
        } catch (RuntimeException e) {
            Assertions.assertEquals("id 99 não foi encontrado em nosso banco.", e.getMessage());
        }
    }


    @Test
    @DisplayName("should delete by id and id user")
    public final void shouldDeleteByIdAndIdUser() {
        var userFactory = new UserFactory().createWithRoleUser("exemplrwwwo1@gmail.com");

        this.userCreateService.create(userFactory);

        LoginDTO loginDTO = new LoginDTO();

        loginDTO.setEmail(userFactory.getEmail());
        loginDTO.setPassword("senhasenha");

        var sign_in = this.authService.signIn(loginDTO);

        var jobCreated = new JobFactory().create(sign_in.getBody().getId(), sign_in.getBody().getToken());

        var job = this.jobRepository.save(jobCreated);

        var apply = new ApplyFactory().create(sign_in.getBody().getId(), job.getId(), sign_in.getBody().getToken());

        var applyCreated = this.matchingRepository.save(apply);

        ResponseEntity<Matching> res = this.matchingDeleteService.deleteByIdAndIdUser(applyCreated.getId(), sign_in.getBody().getId());

        Assertions.assertEquals(HttpStatus.ACCEPTED, res.getStatusCode());
    }

    @DisplayName("should fail in delete by id and id user")
    public final void shouldFailInDeleteByIdAndIdUser() {
        try {
            this.matchingDeleteService.deleteByIdAndIdUser(99l, 99l);
        } catch (RuntimeException e) {
            Assertions.assertEquals("id 99l não foi encontrado em nosso banco.", e.getMessage());
        }
     }


    @Test
    @DisplayName("should delete by id and id job")
    public final void shouldDeleteByIdAndIdJob() {
        var userFactory = new UserFactory().createWithRoleUser("exemplwwwoq1@gmail.com");

        this.userCreateService.create(userFactory);

        LoginDTO loginDTO = new LoginDTO();

        loginDTO.setEmail(userFactory.getEmail());
        loginDTO.setPassword("senhasenha");

        var sign_in = this.authService.signIn(loginDTO);

        var jobCreated = new JobFactory().create(sign_in.getBody().getId(), sign_in.getBody().getToken());

        var job = this.jobRepository.save(jobCreated);

        var apply = new ApplyFactory().create(sign_in.getBody().getId(), job.getId(), sign_in.getBody().getToken());

        var applyCreated = this.matchingRepository.save(apply);

        ResponseEntity<Matching> res = this.matchingDeleteService.deleteByIdAndIdJob(applyCreated.getId(), job.getId());

        Assertions.assertEquals(HttpStatus.ACCEPTED, res.getStatusCode());
    }

    @Test
    @DisplayName("should fail in delete by id and id job")
    public final void shouldFailInDeleteByIdAndIdJob() {
        try {
            this.matchingDeleteService.deleteByIdAndIdJob(99l, 99l);
        } catch (RuntimeException e) {
            Assertions.assertEquals("id 99 e id_job 99 não foi encontrado em nosso banco.", e.getMessage());
        }
     }


    @Test
    @DisplayName("should destroyer all applycations")
    public final void shouldDestroyerAllApplycations() {
            var res = this.matchingDeleteService.destroyer();
            Assertions.assertEquals(HttpStatus.ACCEPTED, res.getStatusCode());
        }

}
