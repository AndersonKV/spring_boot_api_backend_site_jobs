package com.backend.demo.e2e.jobController;

import com.backend.demo.model.Job;
import com.backend.demo.repository.JobRepository;
import com.backend.demo.repository.UserRepository;
import com.backend.demo.service.auth.AuthService;
import com.backend.demo.service.user.UserCreateService;
import com.backend.demo.utils.JobFactory;
import com.backend.demo.utils.LoginFactory;
import com.backend.demo.utils.UserFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("E2E - jobDeleteController")
public class JobDeleteControllerTest {


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


    @AfterEach
    void tearDown() {
        this.userRepository.deleteAll();
        this.jobRepository.deleteAll();
    }

    @Test
    @DisplayName("should delete by id")
    void shouldDeleteById() throws Exception {
        var userRoleCompany = new UserFactory().createUserRoleCompany("sdaawwsd@gmail.com");

        this.userCreateService.create(userRoleCompany);

        var loginDTO = new LoginFactory().create(userRoleCompany.getEmail(), "senhasenha");

        var auth = this.authService.signIn(loginDTO);

        var jobCreate = new JobFactory().create(auth.getBody().getId(), auth.getBody().getToken());

        var job = this.jobRepository.save(jobCreate);

       mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/jobs/delete/" + asJsonString(job.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());


    }

    @Test
    @DisplayName("should fail in delete by id")
    void shouldFailInDeleteById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/jobs/delete/" + asJsonString(9999))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());


    }

    @Test
    @DisplayName("should delete all jobs")
    void shouldDeleteAllJobs() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/jobs/destroyer")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
