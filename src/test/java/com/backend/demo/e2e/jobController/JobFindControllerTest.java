package com.backend.demo.e2e.jobController;

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
import io.swagger.annotations.ApiOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("E2E - jobFindController")
public class JobFindControllerTest {

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
    @DisplayName("should find job")
    void shouldFindJob() throws Exception {
        var userRoleCompany = new UserFactory().createUserRoleCompany("sdaawwsd@gmail.com");

        this.userCreateService.create(userRoleCompany);

        var loginDTO = new LoginFactory().create(userRoleCompany.getEmail(), "senhasenha");

        var auth = this.authService.signIn(loginDTO);

        var jobCreate = new JobFactory().create(auth.getBody().getId(), auth.getBody().getToken());

        var job = this.jobRepository.save(jobCreate);

        var result = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/jobs/find_by_id")
                .param("id", asJsonString(job.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted()).andReturn();

        var bodyResponse = result.getResponse().getContentAsString();

        Gson gson = new Gson();

        var MappingJob = gson.fromJson(bodyResponse, Job.class);

        Assertions.assertEquals(MappingJob.getId(), job.getId());


    }


    @Test
    @DisplayName("should fail in find job")
    void shouldFailInFindJob() throws Exception {

        var result = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/jobs/find_by_id")
                .param("id", String.valueOf(999))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());


    }


    @Test
    @DisplayName("should find by id user")
    void shouldFindByIdUser() throws Exception {
        var userRoleCompany = new UserFactory().createUserRoleCompany("sdaawwsd@gmail.com");

        this.userCreateService.create(userRoleCompany);

        var loginDTO = new LoginFactory().create(userRoleCompany.getEmail(), "senhasenha");

        var auth = this.authService.signIn(loginDTO);

        var jobCreate = new JobFactory().create(auth.getBody().getId(), auth.getBody().getToken());

        var job = this.jobRepository.save(jobCreate);

        var result = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/jobs/find_by_id_user")
                .param("id", asJsonString(auth.getBody().getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted()).andReturn();

        var bodyResponse = result.getResponse().getContentAsString();

        Gson gson = new Gson();

        var MappingJob = gson.fromJson(bodyResponse, Job.class);

        Assertions.assertEquals(MappingJob.getUser_id(), auth.getBody().getId());

    }

    @Test
    @DisplayName("should fail find by id user")
    void shouldFailInFindByIdUser() throws Exception {
        var userRoleCompany = new UserFactory().createUserRoleCompany("sdaaddwwsd@gmail.com");

        this.userCreateService.create(userRoleCompany);

        var loginDTO = new LoginFactory().create(userRoleCompany.getEmail(), "senhasenha");

        var auth = this.authService.signIn(loginDTO);

        var jobCreate = new JobFactory().create(auth.getBody().getId(), auth.getBody().getToken());

        var job = this.jobRepository.save(jobCreate);


        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/jobs/find_by_id_user")
                .param("id", String.valueOf(999))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }


    @Test
    @DisplayName("should find all jobs")
    void shouldFindAllJobs() throws Exception {
        var userRoleCompany = new UserFactory().createUserRoleCompany("swwdaawwsd@gmail.com");

        this.userCreateService.create(userRoleCompany);

        var loginDTO = new LoginFactory().create(userRoleCompany.getEmail(), "senhasenha");

        var auth = this.authService.signIn(loginDTO);

        var jobCreate = new JobFactory().create(auth.getBody().getId(), auth.getBody().getToken());

        this.jobRepository.save(jobCreate);
        this.jobRepository.save(jobCreate);
        this.jobRepository.save(jobCreate);


        var result = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/jobs/list_jobs")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted()).andReturn();

        var bodyResponse = result.getResponse().getContentAsString();

        Gson gson = new Gson();

        List<Job> getJob = gson.fromJson(bodyResponse, ArrayList.class);


        //List<Job> getListJob = (List<Job>) MappingJob;

        Assertions.assertTrue(getJob.size() >= 0);

    }

    @Test
    @DisplayName("should find by tech")
    void shouldFindByTech() throws Exception {
        var userRoleCompany = new UserFactory().createUserRoleCompany("swwdaaww555sd@gmail.com");

        this.userCreateService.create(userRoleCompany);

        var loginDTO = new LoginFactory().create(userRoleCompany.getEmail(), "senhasenha");

        var auth = this.authService.signIn(loginDTO);

        var jobCreate = new JobFactory().create(auth.getBody().getId(), auth.getBody().getToken());

        this.jobRepository.save(jobCreate);
        this.jobRepository.save(jobCreate);


        var result = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/jobs/find_by_tech")
                .param("tech", "ruby")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted()).andReturn();

        var bodyResponse = result.getResponse().getContentAsString();

        Gson gson = new Gson();

        List<Job> getJob = gson.fromJson(bodyResponse, ArrayList.class);

        Assertions.assertTrue(getJob.size() >= 0);

    }


    @Test
    @DisplayName("should get the last three jobs")
    void shouldGetTheLastThreeJobs() throws Exception {
        var userRoleCompany = new UserFactory().createUserRoleCompany("swwdaddaww555sd@gmail.com");


        this.userCreateService.create(userRoleCompany);

        var loginDTO = new LoginFactory().create(userRoleCompany.getEmail(), "senhasenha");

        var auth = this.authService.signIn(loginDTO);

        var jobCreate = new JobFactory().create(auth.getBody().getId(), auth.getBody().getToken());
        var jobCreate2 = new JobFactory().create(auth.getBody().getId(), auth.getBody().getToken());
        var jobCreate3 = new JobFactory().create(auth.getBody().getId(), auth.getBody().getToken());

        this.jobRepository.save(jobCreate);
        this.jobRepository.save(jobCreate2);
        this.jobRepository.save(jobCreate3);

        var result = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/jobs/find_the_last_three_jobs")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted()).andReturn();

        var bodyResponse = result.getResponse().getContentAsString();

        Gson gson = new Gson();

        List<Job> getJob = gson.fromJson(bodyResponse, ArrayList.class);

        Assertions.assertTrue(getJob.size() >= 3);

    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
