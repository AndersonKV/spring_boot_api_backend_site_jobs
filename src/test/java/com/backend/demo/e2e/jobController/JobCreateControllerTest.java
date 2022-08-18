package com.backend.demo.e2e.jobController;

import com.backend.demo.DTO.LoginDTO;
import com.backend.demo.enums.UserRole;
import com.backend.demo.model.Job;
import com.backend.demo.model.User;
import com.backend.demo.repository.JobRepository;
import com.backend.demo.repository.UserRepository;
import com.backend.demo.service.auth.AuthService;
import com.backend.demo.service.job.JobCreateService;
import com.backend.demo.service.job.JobDeleteService;
import com.backend.demo.service.job.JobFindService;
import com.backend.demo.service.user.UserCreateService;
import com.backend.demo.utils.JobFactory;
import com.backend.demo.utils.LoginFactory;
import com.backend.demo.utils.UserFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.Assert;
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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.validation.constraints.AssertTrue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("E2E - jobCreateController")
public class JobCreateControllerTest {
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwiaWF0IjoxNjYwNzQ2NzQwLCJzdWIiOiIkMmEkMTAkRFFYRmE5Si9BY0NFN1ZJd3M5TlRGZUxRZTh6MzBVeGFMdVZQTFVtWnJuZXE3Qmdrd2VPUEMiLCJpc3MiOiJhbmRlcnNvbiIsImV4cCI6MTY2MDc0NzM0MH0.jbWvvJTyKATbAHyIxu7Kju7De-8F-QJUj4iSkcyG9sr";
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
    }

    @Test
    @DisplayName("should create job")
    void shouldCreateJob() throws Exception {
        var userRoleCompany = new UserFactory().createUserRoleCompany("sdaasd@gmail.com");

        this.userCreateService.create(userRoleCompany);

        var loginDTO = new LoginFactory().create(userRoleCompany.getEmail(), "senhasenha");

        var auth = this.authService.signIn(loginDTO);

        var jobCreate = new JobFactory().create(auth.getBody().getId(), auth.getBody().getToken());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/jobs/create")
                .content(asJsonString(jobCreate))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }


    @Test
    @DisplayName("should fail in create job without id_user")
    void shouldFailInCreateJobWithoutIdUser() throws Exception {
        var userRoleCompany = new UserFactory().createUserRoleCompany("sdaasd@gmail.com");

        var userCreate = this.userCreateService.create(userRoleCompany);

        var loginDTO = new LoginFactory().create(userRoleCompany.getEmail(), "senhasenha");

        var auth = this.authService.signIn(loginDTO);

        var jobCreate = new JobFactory().create(99l, auth.getBody().getToken());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/jobs/create")
                .content(asJsonString(jobCreate))
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
