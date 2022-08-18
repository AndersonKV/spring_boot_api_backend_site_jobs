package com.backend.demo.integration.matchingService;

import com.backend.demo.DTO.LoginDTO;
import com.backend.demo.model.Matching;
import com.backend.demo.repository.MatchingRepository;
import com.backend.demo.repository.JobRepository;
import com.backend.demo.repository.UserRepository;
import com.backend.demo.service.matching.MatchingCreateService;
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

import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
@DisplayName("TEST INTEGRATION - MatchingFindService")
public class MatchingFindServiceTest {
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
    @DisplayName("should find by id")
    public final void shouldFindById() {
        var userFactory = new UserFactory().createWithRoleUser("exemplwwwo5551@gmail.com");

        this.userCreateService.create(userFactory);

        LoginDTO loginDTO = new LoginDTO();

        loginDTO.setEmail(userFactory.getEmail());
        loginDTO.setPassword("senhasenha");

        var sign_in = this.authService.signIn(loginDTO);

        var jobCreated = new JobFactory().create(sign_in.getBody().getId(), sign_in.getBody().getToken());

        var job = this.jobRepository.save(jobCreated);

        var apply = new ApplyFactory().create(sign_in.getBody().getId(), job.getId(), sign_in.getBody().getToken());

        var applyCreated = this.matchingRepository.save(apply);

        var res = this.matchingFindService.findById(applyCreated.getId());

        Assertions.assertEquals(applyCreated.getId(), res.getBody().getId());
        Assertions.assertEquals(HttpStatus.ACCEPTED, res.getStatusCode());
    }

    @Test
    @DisplayName("should fail in find matching")
    public final void shouldFailFindMatching() {
        try {
            this.matchingFindService.findById(99l);
        } catch (RuntimeException e) {
            Assertions.assertEquals("id não encontrado", e.getMessage());
        }
    }

    @Test
    @DisplayName("should find all matching")
    public final void shouldFindAllMatching() {
        var userFactory = new UserFactory().createWithRoleUser("exemplwwwwwwo1@gmail.com");

        this.userCreateService.create(userFactory);

        LoginDTO loginDTO = new LoginDTO();

        loginDTO.setEmail(userFactory.getEmail());
        loginDTO.setPassword("senhasenha");

        var sign_in = this.authService.signIn(loginDTO);

        var jobCreated = new JobFactory().create(sign_in.getBody().getId(), sign_in.getBody().getToken());

        var job = this.jobRepository.save(jobCreated);

        var apply = new ApplyFactory().create(sign_in.getBody().getId(), job.getId(), sign_in.getBody().getToken());

        var applyCreated = this.matchingRepository.save(apply);

        var res = this.matchingFindService.findById(applyCreated.getId());

        ResponseEntity<List<Matching>> find = this.matchingFindService.findAll();
        Assertions.assertEquals(HttpStatus.ACCEPTED, find.getStatusCode());
        Assertions.assertTrue(find.getBody().size() >= 1);

    }

    @Test
    @DisplayName("should find all matching")
    public final void shouldFindByIdUser() {
        var userFactory = new UserFactory().createWithRoleUser("exemprrwwlwwwwwwo1@gmail.com");

        this.userCreateService.create(userFactory);

        LoginDTO loginDTO = new LoginDTO();

        loginDTO.setEmail(userFactory.getEmail());
        loginDTO.setPassword("senhasenha");

        var sign_in = this.authService.signIn(loginDTO);

        var jobCreated = new JobFactory().create(sign_in.getBody().getId(), sign_in.getBody().getToken());

        var job = this.jobRepository.save(jobCreated);

        var apply = new ApplyFactory().create(sign_in.getBody().getId(), job.getId(), sign_in.getBody().getToken());

        var applyCreated = this.matchingRepository.save(apply);

        ResponseEntity<Matching> find = this.matchingFindService.findByIdUser(applyCreated.getId_user());

        Assertions.assertEquals(HttpStatus.ACCEPTED, find.getStatusCode());
    }

    @Test
    @DisplayName("should fail in find by id user")
    public final void shouldFailInFindByIdUser() {
        try {
            this.matchingFindService.findByIdUser(99l);
        } catch (RuntimeException e) {
            Assertions.assertEquals("id não encontrado", e.getMessage());
        }

    }

    @Test
    @DisplayName("should find by id job")
    public final void shouldFindByIdJob() {
        var userFactory = new UserFactory().createWithRoleUser("55sss@gmail.com");

        this.userCreateService.create(userFactory);

        LoginDTO loginDTO = new LoginDTO();

        loginDTO.setEmail(userFactory.getEmail());
        loginDTO.setPassword("senhasenha");

        var sign_in = this.authService.signIn(loginDTO);

        var jobCreated = new JobFactory().create(sign_in.getBody().getId(), sign_in.getBody().getToken());

        var job = this.jobRepository.save(jobCreated);

        var apply = new ApplyFactory().create(sign_in.getBody().getId(), job.getId(), sign_in.getBody().getToken());

        var applyCreated = this.matchingRepository.save(apply);

        ResponseEntity<Matching> find = this.matchingFindService.findByIdJob(applyCreated.getId_job());

        Assertions.assertEquals(HttpStatus.ACCEPTED, find.getStatusCode());
    }

    @Test
    @DisplayName("should fail in find by id job")
    public final void shouldFailInFindByIdJob() {
        try {
            this.matchingFindService.findByIdJob(99l);
        } catch (RuntimeException e) {
            Assertions.assertEquals("id não encontrado", e.getMessage());
        }

    }

    @Test
    @DisplayName("should find by id and id job and id user")
    public final void shouldFindByIdAndIdJobAndIdUser() {
        try {
            var userFactory = new UserFactory().createWithRoleUser("exemprrwwlwwwwrwwor1@gmail.com");

            this.userCreateService.create(userFactory);

            LoginDTO loginDTO = new LoginDTO();

            loginDTO.setEmail(userFactory.getEmail());
            loginDTO.setPassword("senhasenha");

            var sign_in = this.authService.signIn(loginDTO);

            var jobCreated = new JobFactory().create(sign_in.getBody().getId(), sign_in.getBody().getToken());

            var job = this.jobRepository.save(jobCreated);

            var apply = new ApplyFactory().create(sign_in.getBody().getId(), job.getId(), sign_in.getBody().getToken());

            var applyCreated = this.matchingRepository.save(apply);

            ResponseEntity<Matching> find = this.matchingFindService.findByIdAndUserIdAndIdJob(applyCreated.getId(), applyCreated.getId_user(), applyCreated.getId_job());
            Assertions.assertEquals(HttpStatus.ACCEPTED, find.getStatusCode());
        } catch (RuntimeException e) {
            Assertions.assertEquals("id não encontrado", e.getMessage());
        }

    }

    @Test
    @DisplayName("should fail in find by id and id job and id user")
    public final void shouldFailInFindByIdAndIdJobAndIdUser() {
        try {
           this.matchingFindService.findByIdAndUserIdAndIdJob(88l, 88l, 88l);
        } catch (RuntimeException e) {
            Assertions.assertEquals("nenhuma informações foi encontrada com os ids: id 88, id_user 88, id_job 88", e.getMessage());
        }

    }

}

