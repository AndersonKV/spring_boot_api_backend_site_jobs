package com.backend.demo.e2e.matchingController;

import com.backend.demo.DTO.MatchingDTO;
import com.backend.demo.model.Matching;
import com.backend.demo.model.User;
import com.backend.demo.repository.JobRepository;
import com.backend.demo.repository.MatchingRepository;
import com.backend.demo.repository.UserRepository;
import com.backend.demo.service.auth.AuthService;
import com.backend.demo.service.job.JobCreateService;
import com.backend.demo.service.job.JobDeleteService;
import com.backend.demo.service.job.JobFindService;
import com.backend.demo.service.matching.MatchingCreateService;
import com.backend.demo.service.user.UserCreateService;
import com.backend.demo.utils.JobFactory;
import com.backend.demo.utils.LoginFactory;
import com.backend.demo.utils.UserFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("E2E - matchingFindController")
public class matchingFindControllerTest {

    @Autowired
    MatchingCreateService matchingCreateService;

    @Autowired
    MatchingRepository matchingRepository;


    @Autowired
    private UserCreateService userCreateService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;

    @Autowired
    JobRepository jobRepository;


    @Autowired
    AuthService authService;

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
        this.matchingRepository.deleteAll();
    }

    @Test
    @DisplayName("should find matching")
    void shouldFindMatchingJob() throws Exception {

        //cria company factory
        var userRoleCompany = new UserFactory().createUserRoleCompany("33333@gmail.com");
        //salva no service
        var saveCompany = this.userCreateService.create(userRoleCompany);
        Assertions.assertEquals(saveCompany.getStatusCode(), HttpStatus.CREATED);

        //faz login
        var loginDTO = new LoginFactory().create(userRoleCompany.getEmail(), "senhasenha");
        //autenticação
        var auth = this.authService.signIn(loginDTO);
        Assertions.assertEquals(auth.getStatusCode(), HttpStatus.ACCEPTED);

        //cria um job facotry
        var jobFactory = new JobFactory().create(auth.getBody().getId(), auth.getBody().getToken());
        //cria o job
        var jobCreated = this.jobRepository.save(jobFactory);
        Assertions.assertEquals(jobCreated.getUser_id(), jobFactory.getId());

        var userRole = new UserFactory().createWithRoleUser("222@gmail.com");
        var saveUser = this.userCreateService.create(userRole);
        var loginUser = new LoginFactory().create(userRole.getEmail(), "senhasenha");


        var authUser = this.authService.signIn(loginUser);

        Matching matching = new Matching();
        matching.setId_job(jobCreated.getId());
        matching.setId_user(authUser.getBody().getId());
        matching.setToken(authUser.getBody().getToken());

        var createdMatching = matchingRepository.save(matching);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/matchings/find_by_id")
                .param("id", asJsonString(createdMatching.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

    }

    @Test
    @DisplayName("should fail in find matching")
    void shouldFailInMatchingJob() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/matchings/find_by_id")
                .param("id", String.valueOf(888l))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}


